package com.coursework.featureSearchBooks.booksList.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList

@Immutable
internal data class BooksListViewState(
    val searchInput: String = "",
    val showAddBookButton: Boolean = false,
)

@Immutable
internal data class BookViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: ComposeList<String>,
    val publisher: String?,
    val hasPdfVersion: Boolean,
)
