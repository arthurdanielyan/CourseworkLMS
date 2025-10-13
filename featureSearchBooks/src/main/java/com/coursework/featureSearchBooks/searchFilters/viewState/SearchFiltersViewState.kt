package com.coursework.featureSearchBooks.searchFilters.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.emptyComposeList

@Immutable
data class SearchFiltersViewState(
    val authorInput: String = "",
    val publicationYearInput: String = "",
    val topCategories: ComposeList<FilterViewState> = emptyComposeList(),
    val topLanguages: ComposeList<FilterViewState> = emptyComposeList(),
    val availabilities: ComposeList<FilterViewState> = emptyComposeList(),
    val topTeachers: ComposeList<FilterViewState> = emptyComposeList(),
) {

    companion object {
        val MockCategories = listOf(
            FilterViewState(
                id = 1,
                displayName = "Physics",
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = "Analytic Geometry and Linear Algebra",
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = "Programming",
                isSelected = false,
            ),
            FilterViewState(
                id = 4,
                displayName = "Philosophy",
                isSelected = false,
            ),
            FilterViewState(
                id = 5,
                displayName = "History",
                isSelected = false,
            ),
            FilterViewState(
                id = 6,
                displayName = "Network",
                isSelected = false,
            ),
        )
        val MockLanguages = listOf(
            FilterViewState(
                id = 1,
                displayName = "Armenian",
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = "Russian",
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = "English",
                isSelected = false,
            ),
        )
        val MockTeachers = listOf(
            FilterViewState(
                id = 1,
                displayName = "E. Hovhannisyan",
                isSelected = false,
            ),
            FilterViewState(
                id = 2,
                displayName = "T. Ganovich",
                isSelected = false,
            ),
            FilterViewState(
                id = 3,
                displayName = "R. Hakobyan",
                isSelected = false,
            ),
            FilterViewState(
                id = 4,
                displayName = "A. Baghdasaryan",
                isSelected = false,
            ),
            FilterViewState(
                id = 5,
                displayName = "L. Andreasyan",
                isSelected = false,
            ),
            FilterViewState(
                id = 6,
                displayName = "E. Virabyan",
                isSelected = false,
            ),
        )
    }
}

@Immutable
data class FilterViewState(
    val id: Int,
    val displayName: String,
    val isSelected: Boolean,
)
