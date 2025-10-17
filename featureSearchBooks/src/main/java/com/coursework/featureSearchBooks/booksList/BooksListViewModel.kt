package com.coursework.featureSearchBooks.booksList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.destinations.BookDetailsDestination
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.navigation.destinations.LoginDestination
import com.coursework.domain.model.SearchFilters
import com.coursework.domain.model.UserType
import com.coursework.domain.usecases.GetUserTypeUseCase
import com.coursework.featureSearchBooks.booksList.mapper.BookViewStateMapper
import com.coursework.featureSearchBooks.booksList.viewState.BookViewState
import com.coursework.featureSearchBooks.booksList.viewState.BooksListViewState
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class BooksListViewModel(
    private val appRouter: AppRouter,
    private val booksPagingSourceFactory: BooksPagingSource.Factory,
    private val bookViewStateMapper: BookViewStateMapper,
    private val getUserTypeUseCase: GetUserTypeUseCase,
) : ViewModel(), BooksListUiCallbacks {

    private companion object {
        private const val SearchQueryDebounce = 300L
        private const val BooksPerPage = 20
    }

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val searchQuery = MutableStateFlow("")
    private val showAddBookButton = MutableStateFlow(false)

    private val searchFilters = MutableStateFlow(SearchFilters())

    val booksPagingSource = combine(
        refreshEvent,
        searchQuery.debounce(SearchQueryDebounce),
        searchFilters,
        ::Triple
    ).flatMapLatest { (_, query, filters) ->
        Pager(PagingConfig(pageSize = BooksPerPage)) {
            booksPagingSourceFactory(
                query = query,
                filters = filters
            )
        }.flow
    }.cachedIn(viewModelScope)
        .mapLatest { pagingData ->
            pagingData.map { book ->
                bookViewStateMapper(book)
            }
        }.stateInWhileSubscribed(
            viewModelScope,
            PagingData.empty(
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(endOfPaginationReached = true),
                    append = LoadState.Loading,
                )
            )
        )

    val uiState = combine(
        searchQuery,
        showAddBookButton,
    ) { searchQuery, showAddBookButton ->

        BooksListViewState(
            searchInput = searchQuery,
            showAddBookButton = showAddBookButton,
        )
    }.stateInWhileSubscribed(viewModelScope, BooksListViewState())

    init {
        getUserType()
        onRefresh()
    }

    private fun getUserType() {
        viewModelScope.launch {
            showAddBookButton.update {
                getUserTypeUseCase() == UserType.Teacher
            }
        }
    }

    override fun onGetFilterResult(filters: SearchFilters) {
        this.searchFilters.update {
            filters
        }
    }

    override fun onSearchQueryType(query: String) {
        searchQuery.update { query }
    }

    override fun onBookClick(book: BookViewState) {
        appRouter.navigate(
            BookDetailsDestination(
                id = book.id
            )
        )
    }

    override fun onSearchFiltersClick() {
        appRouter.navigate(SearchFiltersDestination)
    }

    override fun onAddBookClick() {
        appRouter.navigate(
            EditBookDestination(
                isNewBook = true
            )
        )
    }

    override fun onLogoutClick() {
        // TODO: Implement logout logic
        appRouter.navigate(
            destination = LoginDestination,
            popAll = true,
        )
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}