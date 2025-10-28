package com.coursework.featureMyAddedBooks.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.DataLoadingState

@Immutable
data class MyAddedBooksViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
)
