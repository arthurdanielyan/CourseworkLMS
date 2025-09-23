package com.coursework.featureEditBook.presentation

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.DataLoadingState

@Immutable
data class EditBookViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
    val details: EditBookDetailsViewState? = null,
)

@Immutable
data class EditBookDetailsViewState(
    val titleInput: String = "",
    val subtitleInput: String = "", // optional
    val authorsInput: String = "",
    val publisherInput: String = "", // optional
    val publicationYearInput: String = "", // optional
    val editionInput: String = "", // optional
    val categoriesInput: String = "",
    val pdfUrlInput: String = "", // optional
    val coverImageUrlInput: String = "", // optional
    val totalCopiesInput: String = "",
    val copiesAvailableInput: String = "",
    val languageInput: String = "",
    val isReferenceOnlyChecked: Boolean = false,
)