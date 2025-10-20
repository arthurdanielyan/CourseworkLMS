package com.coursework.featureEditBook.presentation.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.emptyComposeList

@Immutable
internal data class EditBookViewState(
    val topBarTitle: String = "",
    val details: EditBookDetailsViewState = EditBookDetailsViewState(),
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
)

@Immutable
internal data class EditBookDetailsViewState(
    val titleInput: String = "",
    val subtitleInput: String = "", // optional
    val authorsInput: String = "",
    val publisherInput: String = "", // optional
    val publicationYearInput: String = "", // optional
    val editionInput: String = "", // optional
    val categories: ComposeList<FilterViewState> = emptyComposeList(), // optional
    val languages: ComposeList<FilterViewState> = emptyComposeList(), // optional
    val bookPdf: BookPdfViewState = BookPdfViewState(), // optional
    val coverImage: CoverImageViewState = CoverImageViewState(), // optional
    val totalCopiesInput: String = "",
    val isReferenceOnlyChecked: Boolean = false,
) {

    val isTotalCopiesInputValid = totalCopiesInput.isNotBlank() &&
            totalCopiesInput.all { it.isDigit() }

    val isPublicationYearInputValid = publicationYearInput.all { it.isDigit() }

}

@Immutable
data class BookPdfViewState(
    val pdfUrl: String? = null,
    val localFileSelected: Boolean = false,
    val displayName: String = "",
) {
    val isUploaded = pdfUrl != null || localFileSelected
}

@Immutable
data class CoverImageViewState(
    val url: String? = null,
    val localUri: String? = null,
) {

    val isUploaded = url != null || localUri != null
}
