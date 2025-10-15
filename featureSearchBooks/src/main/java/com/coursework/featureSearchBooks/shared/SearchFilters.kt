package com.coursework.featureSearchBooks.shared

import androidx.compose.runtime.Immutable

@Immutable
data class SearchFilters(
    val author: String? = null,
    val publicationYear: Int? = null,
    val selectedCategoryIds: List<Int> = emptyList(),
    val selectedLanguageIds: List<Int> = emptyList(),
    val selectedAvailabilityIds: List<Int> = emptyList(),
    val selectedTeacherIds: List<Int> = emptyList(),
)