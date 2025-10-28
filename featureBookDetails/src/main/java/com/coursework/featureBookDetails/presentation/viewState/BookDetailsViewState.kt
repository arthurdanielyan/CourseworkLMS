package com.coursework.featureBookDetails.presentation.viewState

import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.viewState.DataLoadingState

@Immutable
internal data class BookDetailsScreenViewState(
    val dataLoadingState: DataLoadingState = DataLoadingState.Loading,
    val bookDetails: BookDetailsViewState? = null,
)

@Immutable
internal data class BookDetailsViewState(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: List<String>,
    val publisher: String?,
    val publicationYear: Int?,
    val edition: String?,
    val categories: List<String>,
    val hasPdfVersion: Boolean,
    val pdfUrl: String?,
    val coverImageUrl: String?,
    val totalCopies: Int,
    val copiesAvailable: Int,
    val language: String,
    val isReferenceOnly: Boolean,
    val isReserveButtonLoading: Boolean = false,
) {
    val showBookButton = copiesAvailable > 0 && isReferenceOnly.not()
}

@Immutable
sealed interface BookDetailsDialogState {

    data class ReturnDatePickerDialog(
        val maxDateMillis: Long = Long.MAX_VALUE,
        val inConfirmButtonLoading: Boolean = false
    ) : BookDetailsDialogState

    data class MessageDialog(
        val message: String
    ) : BookDetailsDialogState
}
