package com.coursework.domain.model.books

data class BookDetails(
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
    val pdfTitle: String?,
    val coverImageUrl: String?,
    val totalCopies: Int,
    val copiesAvailable: Int,
    val language: String,
    val isReferenceOnly: Boolean,
)
