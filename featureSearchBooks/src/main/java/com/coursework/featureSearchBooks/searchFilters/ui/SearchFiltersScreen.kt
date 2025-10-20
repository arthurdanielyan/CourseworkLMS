package com.coursework.featureSearchBooks.searchFilters.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.SpacerHeight
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.filters.FilterSection
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.corePresentation.extensions.ObserveEffects
import com.coursework.corePresentation.extensions.OnLifecycleEvents
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersUiCallbacks
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersUiEffect
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersViewModel
import com.coursework.featureSearchBooks.searchFilters.viewState.SearchFiltersViewState
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import org.koin.androidx.compose.koinViewModel
import com.coursework.corePresentation.R.string as CoreStrings
import com.coursework.featureSearchBooks.R.string as Strings

@Composable
fun SearchFiltersScreen(
    sharedViewModel: SearchBooksSharedViewModel,
) {
    val viewModel = koinViewModel<SearchFiltersViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    OnLifecycleEvents {
        onCreate {
            viewModel.emitInitialFilters(sharedViewModel.searchFilters.value)
        }
    }

    ObserveEffects(viewModel.uiEffects) { effect ->
        when (effect) {
            is SearchFiltersUiEffect.SetResult -> {
                sharedViewModel.setResult(effect.filters)
                viewModel.onResultSent()
            }
        }
    }

    SearchFiltersScreen(
        state = state,
        callbacks = viewModel
    )
}

@Composable
private fun SearchFiltersScreen(
    state: SearchFiltersViewState,
    callbacks: SearchFiltersUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBarWithBackButton(
            title = stringResource(Strings.filters),
            modifier = Modifier.fillMaxWidth(),
            onBackClick = callbacks::onBackClick
        )
        SearchFiltersContent(
            state = state,
            callbacks = callbacks
        )
    }
}

@Composable
private fun SearchFiltersContent(
    state: SearchFiltersViewState,
    callbacks: SearchFiltersUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 32.dp,
            )
    ) {
        TextField(
            value = state.authorInput,
            onValueChange = callbacks::onAuthorType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(CoreStrings.author),
        )
        SpacerHeight(16.dp)
        TextField(
            value = state.publicationYearInput,
            onValueChange = callbacks::onPublicationYearType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(CoreStrings.publication_year),
            keyboardType = KeyboardType.Number,
        )
        SpacerHeight(32.dp)
        FilterSection(
            title = stringResource(CoreStrings.categories),
            filters = state.topCategories,
            onFilterSelect = callbacks::onSelectCategory,
            onShowAllClick = callbacks::onShowAllCategoriesClick,
            showShowAllButton = true,
        )
        SpacerHeight(32.dp)
        FilterSection(
            title = stringResource(CoreStrings.language),
            filters = state.topLanguages,
            onFilterSelect = callbacks::onSelectLanguage,
            onShowAllClick = callbacks::onShowAllLanguagesClick,
        )
        SpacerHeight(32.dp)
        FilterSection(
            title = stringResource(Strings.availability),
            filters = state.availabilities,
            onFilterSelect = callbacks::onSelectAvailability,
        )
        SpacerHeight(32.dp)
        FilterSection(
            title = stringResource(Strings.added_by),
            filters = state.topTeachers,
            onFilterSelect = callbacks::onSelectTeacher,
            onShowAllClick = callbacks::onShowAllTeachersClick,
            showShowAllButton = true,
        )
    }
}
