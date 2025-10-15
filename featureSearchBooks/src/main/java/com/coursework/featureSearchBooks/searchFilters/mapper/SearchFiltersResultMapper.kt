package com.coursework.featureSearchBooks.searchFilters.mapper

import com.coursework.featureSearchBooks.searchFilters.viewState.FilterViewState
import com.coursework.featureSearchBooks.searchFilters.viewState.SearchFiltersViewState
import com.coursework.featureSearchBooks.shared.SearchFilters
import com.coursework.utils.Mapper

class SearchFiltersResultMapper : Mapper<SearchFiltersViewState, SearchFilters> {

    override fun map(from: SearchFiltersViewState): SearchFilters {
        return SearchFilters(
            author = from.authorInput,
            publicationYear = from.publicationYearInput.toIntOrNull(),
            selectedCategoryIds = mapFilterViewStates(from.topCategories),
            selectedLanguageIds = mapFilterViewStates(from.topLanguages),
            selectedAvailabilityIds = mapFilterViewStates(from.availabilities),
            selectedTeacherIds = mapFilterViewStates(from.topTeachers),
        )
    }

    private fun mapFilterViewStates(filters: List<FilterViewState>): List<Int> {
        return filters.filter { it.isSelected }.map { it.id }
    }
}