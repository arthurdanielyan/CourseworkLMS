package com.coursework.featureSearchBooks.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.emptyComposeList

@Immutable
data class SearchBooksViewState(
    val searchInput: String = "",
    val books: ComposeList<BookViewState> = emptyComposeList(),
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
)

@Immutable
data class BookViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: ComposeList<String>,
    val publisher: String?,
    val hasPdfVersion: Boolean,
)
