package com.coursework.featureSearchBooks.searchFilters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.featureSearchBooks.searchFilters.viewState.FilterViewState
import com.coursework.featureSearchBooks.searchFilters.viewState.SearchFiltersViewState
import com.coursework.utils.combine
import com.coursework.utils.stateInWhileSubscribed
import com.coursework.utils.stringProvider.StringProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import com.coursework.featureSearchBooks.R.string as Strings

class SearchFiltersViewModel(
    private val stringProvider: StringProvider,
    private val appRouter: AppRouter,
) : ViewModel(), SearchFiltersUiCallbacks {

    private companion object {
        const val AVAILABLE_FILTER_ID = 1
        const val UNAVAILABLE_FILTER_ID = 2
        const val REFERENCE_ONLY_FILTER_ID = 3
    }

    private val authorInput = MutableStateFlow("")
    private val publicationYearInput = MutableStateFlow("")
    private val topCategories = MutableStateFlow(SearchFiltersViewState.MockCategories)
    private val topLanguages = MutableStateFlow(SearchFiltersViewState.MockLanguages)
    private val availabilities = MutableStateFlow(getAvailabilityFilters())
    private val topTeachers = MutableStateFlow(SearchFiltersViewState.MockTeachers)


    val uiState = combine(
        authorInput,
        publicationYearInput,
        topCategories,
        topLanguages,
        availabilities,
        topTeachers,
    ) { authorInput,
        publicationYearInput,
        topCategories,
        topLanguages,
        availabilities,
        topTeachers ->

        SearchFiltersViewState(
            authorInput = authorInput,
            publicationYearInput = publicationYearInput,
            topCategories = topCategories.toComposeList(),
            topLanguages = topLanguages.toComposeList(),
            availabilities = availabilities.toComposeList(),
            topTeachers = topTeachers.toComposeList(),
        )
    }.stateInWhileSubscribed(viewModelScope, SearchFiltersViewState())

    private fun getAvailabilityFilters(): List<FilterViewState> {
        return listOf(
            FilterViewState(
                id = AVAILABLE_FILTER_ID,
                displayName = stringProvider.string(Strings.available),
                isSelected = false,
            ),
            FilterViewState(
                id = UNAVAILABLE_FILTER_ID,
                displayName = stringProvider.string(Strings.unavailable),
                isSelected = false,
            ),
            FilterViewState(
                id = REFERENCE_ONLY_FILTER_ID,
                displayName = stringProvider.string(Strings.reference_only),
                isSelected = false,
            )
        )
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onAuthorType(value: String) {
        authorInput.update { value }
    }

    override fun onPublicationYearType(value: String) {
        publicationYearInput.update { value }
    }

    override fun onSelectCategory(categoryFilter: FilterViewState) {
        topCategories.update {
            it.map { filter ->
                if (filter.id == categoryFilter.id) {
                    filter.copy(isSelected = filter.isSelected.not())
                } else {
                    filter
                }
            }
        }
    }

    override fun onShowAllCategoriesClick() {
        // TODO: Not yet implemented
    }

    override fun onSelectLanguage(languageFilter: FilterViewState) {
        topLanguages.update {
            it.map { filter ->
                if (filter.id == languageFilter.id) {
                    filter.copy(isSelected = filter.isSelected.not())
                } else {
                    filter
                }
            }
        }
    }

    override fun onShowAllLanguagesClick() {
        // TODO: Not yet implemented
    }

    override fun onSelectAvailability(availabilityFilter: FilterViewState) {
        availabilities.update {
            it.map { filter ->
                if (filter.id == availabilityFilter.id) {
                    filter.copy(isSelected = filter.isSelected.not())
                } else {
                    filter
                }
            }
        }
    }

    override fun onSelectTeacher(availabilityFilter: FilterViewState) {
        topTeachers.update {
            it.map { filter ->
                if (filter.id == availabilityFilter.id) {
                    filter.copy(isSelected = filter.isSelected.not())
                } else {
                    filter
                }
            }
        }
    }

    override fun onShowAllTeachersClick() {
        // TODO: Not yet implemented
    }
}