package com.coursework.featureSearchBooks.presentation

import androidx.compose.runtime.Immutable

@Immutable
interface SearchBooksUiCallbacks {

    fun onSearchQueryType(query: String)
    fun onBookClick(book: BookViewState)
    fun onRefresh()
}