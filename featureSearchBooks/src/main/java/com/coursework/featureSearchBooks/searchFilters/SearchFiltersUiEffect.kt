package com.coursework.featureSearchBooks.searchFilters

import com.coursework.featureSearchBooks.shared.SearchFilters

sealed interface SearchFiltersUiEffect {

    data class SetResult(
        val filters: SearchFilters
    ) : SearchFiltersUiEffect
}