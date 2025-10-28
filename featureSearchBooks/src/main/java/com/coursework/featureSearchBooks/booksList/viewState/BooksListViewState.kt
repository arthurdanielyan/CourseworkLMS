package com.coursework.featureSearchBooks.booksList.viewState

import androidx.compose.runtime.Immutable

@Immutable
internal data class BooksListViewState(
    val searchInput: String = "",
    val showAddBookButton: Boolean = false,
)
