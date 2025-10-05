package com.coursework.featureSearchBooks.presentation

import androidx.compose.runtime.Immutable
import com.coursework.featureSearchBooks.presentation.viewState.BookViewState

@Immutable
internal interface SearchBooksUiCallbacks {

    fun onSearchQueryType(query: String)
    fun onBookClick(book: BookViewState)
    fun onSearchFiltersClick()
    fun onAddBookClick()
    fun onLogoutClick()
    fun onRefresh()
}