package com.coursework.featureSearchBooks.presentation.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.emptyComposeList

@Immutable
internal data class SearchBooksViewState(
    val searchInput: String = "",
    val books: ComposeList<BookViewState> = emptyComposeList(),
    val showAddBookButton: Boolean = false,
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
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
