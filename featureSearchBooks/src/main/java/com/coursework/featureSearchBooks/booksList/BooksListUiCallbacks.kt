package com.coursework.featureSearchBooks.booksList

import androidx.compose.runtime.Immutable
import com.coursework.domain.books.model.SearchFilters
import com.coursework.featureSearchBooks.booksList.viewState.BookViewState

@Immutable
internal interface BooksListUiCallbacks {

    fun onGetFilterResult(filters: SearchFilters)
    fun onSearchQueryType(query: String)
    fun onBookClick(book: BookViewState)
    fun onSearchFiltersClick()
    fun onAddBookClick()
    fun onLogoutClick()
    fun onRefresh()
}