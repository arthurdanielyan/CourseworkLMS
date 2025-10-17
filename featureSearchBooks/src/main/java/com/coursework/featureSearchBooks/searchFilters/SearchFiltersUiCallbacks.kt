package com.coursework.featureSearchBooks.searchFilters

import androidx.compose.runtime.Immutable
import com.coursework.domain.model.SearchFilters
import com.coursework.featureSearchBooks.searchFilters.viewState.FilterViewState

@Immutable
interface SearchFiltersUiCallbacks {

    fun emitInitialFilters(filters: SearchFilters)
    fun onResultSent()
    fun onBackClick()
    fun onAuthorType(value: String)
    fun onPublicationYearType(value: String)
    fun onSelectCategory(categoryFilter: FilterViewState)
    fun onShowAllCategoriesClick()
    fun onSelectLanguage(languageFilter: FilterViewState)
    fun onShowAllLanguagesClick()
    fun onSelectAvailability(availabilityFilter: FilterViewState)
    fun onSelectTeacher(availabilityFilter: FilterViewState)
    fun onShowAllTeachersClick()
}