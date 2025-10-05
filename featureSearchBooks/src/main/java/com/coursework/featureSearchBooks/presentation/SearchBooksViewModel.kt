package com.coursework.featureSearchBooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.navigation.destinations.HomeScreenDestination
import com.coursework.corePresentation.navigation.destinations.LoginDestination
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.model.UserType
import com.coursework.domain.usecases.GetUserTypeUseCase
import com.coursework.domain.usecases.SearchBooksUseCase
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureSearchBooks.BookSearchFiltersDestination
import com.coursework.featureSearchBooks.presentation.mapper.BookViewStateMapper
import com.coursework.featureSearchBooks.presentation.viewState.BookViewState
import com.coursework.featureSearchBooks.presentation.viewState.SearchBooksViewState
import com.coursework.utils.mapList
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SearchBooksViewModel(
    private val appRouter: AppRouter,
    private val searchBooksUseCase: SearchBooksUseCase,
    private val bookViewStateMapper: BookViewStateMapper,
    private val getUserTypeUseCase: GetUserTypeUseCase,
) : ViewModel(), SearchBooksUiCallbacks {

    private companion object {
        private const val SearchQueryDebounce = 300L
    }

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val searchQuery = MutableStateFlow("")
    private val books = MutableStateFlow(emptyList<BookViewState>())
    private val showAddBookButton = MutableStateFlow(false)
    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)

    val uiState = combine(
        searchQuery,
        books,
        showAddBookButton,
        dataLoadingState,
    ) { searchQuery, books, showAddBookButton, dataLoadingState ->

        SearchBooksViewState(
            searchInput = searchQuery,
            books = books.toComposeList(),
            showAddBookButton = showAddBookButton,
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, SearchBooksViewState())

    init {
        getUserType()
        observeQuery()
        onRefresh()
    }

    private fun getUserType() {
        viewModelScope.launch {
            showAddBookButton.update {
                getUserTypeUseCase() == UserType.Teacher
            }
        }
    }

    private fun observeQuery() {
        viewModelScope.launch {
            refreshEvent
                .flatMapLatest {
                    dataLoadingState.update { DataLoadingState.Loading }
                    searchQuery
                        .debounce(SearchQueryDebounce)
                }
                .collectLatest { query ->
                    dataLoadingState.update { DataLoadingState.Loading }
                    searchBooksUseCase(query)
                        .onSuccess { booksResult ->
                            books.update {
                                bookViewStateMapper.mapList(booksResult)
                            }
                            dataLoadingState.update { DataLoadingState.Success }
                        }
                        .onFailure { throwable ->
                            dataLoadingState.update {
                                DataLoadingState.Error
                            }
                        }
                }
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
        appRouter.navigate(BookSearchFiltersDestination)
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
            popUpTo = HomeScreenDestination::class,
            inclusive = true,
        )
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}