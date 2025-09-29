package com.coursework.featureEditBook.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.SecondaryButton
import com.coursework.corePresentation.commonUi.SpacerWidth
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.commonUi.topBar.TopBarWithBackButton
import com.coursework.corePresentation.extensions.ObserveEffects
import com.coursework.corePresentation.extensions.isKeyboardVisible
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.presentation.EditBookUiCallbacks
import com.coursework.featureEditBook.presentation.EditBookUiEffect
import com.coursework.featureEditBook.presentation.EditBookViewModel
import com.coursework.featureEditBook.presentation.EditBookViewState
import com.coursework.featureEditBook.ui.components.CoverImagePicker
import com.coursework.featureEditBook.ui.components.PdfPicker
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

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        callbacks.onFilePicked(uri)
    }

    val context = LocalContext.current
    ObserveEffects(viewModel.uiEffect) {
        when (it) {
            EditBookUiEffect.PickCoverImage -> {
                launcher.launch("image/*")
            }

            EditBookUiEffect.PickPdf -> {
                launcher.launch("application/pdf")
            }

            is EditBookUiEffect.ShowError -> {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

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
    ) {
        TopBarWithBackButton(
            modifier = Modifier.fillMaxWidth(),
            title = state.topBarTitle,
            onBackClick = callbacks::onBackClick
        )
        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            dataLoadingState = state.dataLoadingState,
            onRefresh = {}
        ) {
            EditBookScreenContent(
                state = state,
                callbacks = callbacks,
            )
        }
    }
}

@Composable
private fun EditBookScreenContent(
    state: EditBookViewState,
    callbacks: EditBookUiCallbacks
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isKeyboardVisible by isKeyboardVisible()

    BackHandler {
        if (isKeyboardVisible) {
            keyboardController?.hide()
        } else {
            focusManager.clearFocus(force = true)
            callbacks.onBackClick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Strings.required_fields_annotation),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        TextField(
            value = state.details.titleInput,
            onValueChange = callbacks::onTitleType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.title_label),
            placeholder = stringResource(Strings.title_placeholder),
            isError = state.details.titleInput.isBlank(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.subtitleInput,
            onValueChange = callbacks::onSubtitleType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.subtitle_label),
        )
        TextField(
            value = state.details.authorsInput,
            onValueChange = callbacks::onAuthorsType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.authors_label),
            placeholder = stringResource(Strings.authors_placeholder),
            isError = state.details.authorsInput.isBlank(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.publisherInput,
            onValueChange = callbacks::onPublisherType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.publisher_label),
        )
        TextField(
            value = state.details.publicationYearInput,
            onValueChange = callbacks::onPublicationYearType,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.publication_year_label),
            isError = state.details.isPublicationYearInputValid.not(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.editionInput,
            onValueChange = callbacks::onEditionType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.edition_label),
        )
        TextField(
            value = state.details.categoriesInput,
            onValueChange = callbacks::onCategoriesType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.categories_label),
            placeholder = stringResource(Strings.categories_placeholder),
        )
        PdfPicker(
            bookPdf = state.details.bookPdf,
            onPickFileClick = callbacks::onPickPdfClick,
            onOpenFileClick = callbacks::onOpenPdf,
            onRemoveClick = callbacks::onRemovePdf,
        )
        CoverImagePicker(
            coverImage = state.details.coverImage,
            onPickImageClick = callbacks::onPickCoverImageClick,
            onRemoveClick = callbacks::onRemoveImageClick,
        )
        TextField(
            value = state.details.totalCopiesInput,
            onValueChange = callbacks::onTotalCopiesType,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.total_copies_label),
            placeholder = stringResource(Strings.total_copies_placeholder),
            isError = state.details.isTotalCopiesInputValid.not(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.copiesAvailableInput,
            onValueChange = callbacks::onCopiesAvailableType,
            keyboardType = KeyboardType.Number,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.copies_available_label),
            placeholder = stringResource(Strings.copies_available_placeholder),
            isError = state.details.isCopiesAvailableInputValid.not(),
            errorMessageEnabled = true,
        )
        TextField(
            value = state.details.languageInput,
            onValueChange = callbacks::onLanguageType,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Strings.language_label),
            placeholder = stringResource(Strings.language_placeholder),
            isError = state.details.languageInput.isBlank(),
            errorMessageEnabled = true,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            SecondaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Strings.cancel),
                onClick = callbacks::onCancelClick
            )
            SpacerWidth(16.dp)
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(Strings.publish),
                onClick = callbacks::onPublishClick
            )
        }
    }
}
