package com.coursework.featureSearchBooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.SearchBooksUseCase
import com.coursework.featureSearchBooks.presentation.mapper.BookViewStateMapper
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

class SearchBooksViewModel(
    private val appRouter: AppRouter,
    private val searchBooksUseCase: SearchBooksUseCase,
    private val bookViewStateMapper: BookViewStateMapper,
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
    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)

    val uiState = combine(
        searchQuery,
        books,
        dataLoadingState,
    ) { searchQuery, books, dataLoadingState ->

        SearchBooksViewState(
            searchInput = searchQuery,
            books = books.toComposeList(),
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, SearchBooksViewState())

    init {
        observeQuery()
        onRefresh()
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
        // TODO: Not yet implemented
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}