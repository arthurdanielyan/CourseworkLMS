package com.coursework.featureBookDetails.presentation.viewState

import androidx.compose.runtime.Immutable

@Immutable
internal data class BookDetailsViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: List<String>,
    val publisher: String?,
    val publicationYear: Int?,
    val edition: String?,
    val categories: List<String>,
    val hasPdfVersion: Boolean,
    val pdfUrl: String?,
    val coverImageUrl: String?,
    val totalCopies: Int,
    val copiesAvailable: Int,
    val language: String,
    val isReferenceOnly: Boolean,
)