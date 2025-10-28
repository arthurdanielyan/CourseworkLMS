package com.coursework.corePresentation.viewState.books

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList

@Immutable
data class BookViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: ComposeList<String>,
    val publisher: String?,
    val hasPdfVersion: Boolean,
)