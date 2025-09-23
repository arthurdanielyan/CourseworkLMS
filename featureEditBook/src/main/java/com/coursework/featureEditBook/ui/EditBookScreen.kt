package com.coursework.featureEditBook.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.presentation.EditBookUiCallbacks
import com.coursework.featureEditBook.presentation.EditBookViewModel
import com.coursework.featureEditBook.presentation.EditBookViewState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.coursework.featureEditBook.R.string as Strings

@Composable
fun EditBookScreen(
    destination: EditBookDestination
) {
    val viewModel = koinViewModel<EditBookViewModel>(
        parameters = { parametersOf(destination) }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: EditBookUiCallbacks = viewModel

    EditBookScreen(
        state = state,
        callbacks = callbacks
    )
}

@Composable
private fun EditBookScreen(
    state: EditBookViewState,
    callbacks: EditBookUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        LoadingStatePresenter(
            modifier = Modifier.fillMaxSize(),
            dataLoadingState = state.dataLoadingState,
            onRefresh = {}
        ) {
            TopBarWithBackButton(
                modifier = Modifier.fillMaxWidth(),
                title = state.details?.titleInput ?: stringResource(Strings.add_book),
                onBackClick = callbacks::onBackClick
            )
        }
    }
}
