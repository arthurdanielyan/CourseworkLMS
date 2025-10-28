package com.coursework.featureBookDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.coursework.corePresentation.commonUi.TextButton
import com.coursework.featureBookDetails.presentation.BookDetailsUiCallbacks
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsDialogState
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import com.coursework.corePresentation.R.string as CoreStrings
import com.coursework.featureBookDetails.R.string as Strings

@Composable
internal fun BookDetailsDialog(
    dialogState: BookDetailsDialogState?,
    callbacks: BookDetailsUiCallbacks
) {
    when (dialogState) {
        is BookDetailsDialogState.MessageDialog -> {
            MessageDialog(
                state = dialogState,
                onDismiss = callbacks::onDismissDialog,
            )
        }

        is BookDetailsDialogState.ReturnDatePickerDialog -> {
            ReturnDatePickerDialog(
                state = dialogState,
                onDismiss = callbacks::onDismissDialog,
                onConfirmDate = callbacks::onConfirmDate
            )
        }

        null -> Unit
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReturnDatePickerDialog(
    state: BookDetailsDialogState.ReturnDatePickerDialog,
    onDismiss: () -> Unit,
    onConfirmDate: (Long) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = latestSelectableDate(state.maxDateMillis)
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                text = stringResource(Strings.confirm),
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onConfirmDate(it)
                    }
                }
            )
        },
        dismissButton = {
            TextButton(
                text = stringResource(CoreStrings.cancel),
                onClick = onDismiss
            )
        }
    ) {
        DatePicker(
            state = datePickerState,
            headline = {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            start = 24.dp
                        ),
                    text = stringResource(Strings.select_return_date)
                )
            },
        )
    }
}

@Composable
private fun MessageDialog(
    state: BookDetailsDialogState.MessageDialog,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = MaterialTheme.shapes.medium,
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )
            TextButton(
                modifier = Modifier.align(Alignment.End),
                text = stringResource(Strings.ok),
                onClick = onDismiss,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
private fun latestSelectableDate(millis: Long): SelectableDates = object : SelectableDates {

    private val tomorrowStartMillis = Clock.System.now()
        .toLocalDateTime(TimeZone.UTC)
        .date
        .plus(1, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.UTC)
        .toEpochMilliseconds()

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        if (utcTimeMillis <= tomorrowStartMillis || utcTimeMillis >= millis) return false

        val date = Instant.fromEpochMilliseconds(utcTimeMillis)
            .toLocalDateTime(TimeZone.UTC)
            .date

        return date.dayOfWeek !in listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
    }
}