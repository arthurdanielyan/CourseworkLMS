package com.coursework.featureSearchBooks.searchFilters

import com.coursework.domain.books.model.SearchFilters

sealed interface SearchFiltersUiEffect {

    data class SetResult(
        val filters: SearchFilters
    ) : SearchFiltersUiEffect
}