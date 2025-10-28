package com.coursework.featureBookDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.destinations.BookDetailsDestination
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.getErrorMessage
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.bookDetails.usecases.GetBookDetailsUseCase
import com.coursework.domain.bookDetails.usecases.GetMaxReservationDate
import com.coursework.domain.bookDetails.usecases.ReserveBookUseCase
import com.coursework.domain.books.usecases.DownloadPdfUseCase
import com.coursework.featureBookDetails.presentation.mapper.BookDetailsViewStateMapper
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsDialogState
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsScreenViewState
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsViewState
import com.coursework.utils.stateInWhileSubscribed
import com.coursework.utils.stringProvider.StringProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class BookDetailsViewModel(
    private val destination: BookDetailsDestination,
    private val appRouter: AppRouter,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val downloadPdfUseCase: DownloadPdfUseCase,
    private val getMaxReservationDate: GetMaxReservationDate,
    private val reserveBookUseCase: ReserveBookUseCase,
    private val bookDetailsViewStateMapper: BookDetailsViewStateMapper,
    private val stringProvider: StringProvider
) : ViewModel(), BookDetailsUiCallbacks {

    private var bookDetails: BookDetails? = null

    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)
    private val bookDetailsState = MutableStateFlow<BookDetailsViewState?>(null)
    private val isReserveButtonLoading = MutableStateFlow(false)

    private val refreshEvent = MutableSharedFlow<Unit>(
        replay = 1
    )

    private val _dialogState = MutableStateFlow<BookDetailsDialogState?>(null)
    val dialogState = _dialogState.asStateFlow()

    val uiState = combine(
        dataLoadingState,
        bookDetailsState,
        isReserveButtonLoading,
    ) { dataLoadingState, bookDetails, isReserveButtonLoading ->

        BookDetailsScreenViewState(
            dataLoadingState = dataLoadingState,
            bookDetails = bookDetails?.copy(
                isReserveButtonLoading = isReserveButtonLoading,
            ),
        )
    }.stateInWhileSubscribed(viewModelScope, BookDetailsScreenViewState())

    init {
        observeRefreshEvent()
        onRefresh()
    }

    private fun observeRefreshEvent() {
        viewModelScope.launch {
            refreshEvent
                .collectLatest {
                    dataLoadingState.update { DataLoadingState.Loading }
                    getBookDetailsUseCase(destination.id)
                        .onSuccess { result ->
                            bookDetails = result
                            dataLoadingState.value = DataLoadingState.Success
                            bookDetailsState.update {
                                bookDetailsViewStateMapper.map(result)
                            }
                        }
                        .onFailure { throwable ->
                            dataLoadingState.update { throwable.toDataLoadingState() }
                        }
                }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onDownloadPdfClick() {
        bookDetails?.let {
            downloadPdfUseCase(it)
        }
    }

    @OptIn(ExperimentalTime::class)
    override fun onReserveClick() {
        viewModelScope.launch {
            getMaxReservationDate(
                bookId = destination.id
            ).onSuccess { maxDate ->
                _dialogState.update {
                    BookDetailsDialogState.ReturnDatePickerDialog(
                        maxDateMillis = maxDate.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
                    )
                }
            }.onFailure { throwable ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = stringProvider.getErrorMessage(throwable)
                    )
                }
            }
        }
    }

    override fun onDismissDialog() {
        _dialogState.update { null }
    }

    @OptIn(ExperimentalTime::class)
    override fun onConfirmDate(dateMillis: Long) {
        viewModelScope.launch {
            val returnDate = Instant.fromEpochMilliseconds(dateMillis)
                .toLocalDateTime(TimeZone.UTC).date
            reserveBookUseCase(
                bookId = destination.id,
                returnDate = returnDate
            ).onSuccess { reservationResult ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = reservationResult.displayMessage,
                    )
                }
            }.onFailure { throwable ->
                _dialogState.update {
                    BookDetailsDialogState.MessageDialog(
                        message = stringProvider.getErrorMessage(throwable)
                    )
                }
            }
        }
    }

    override fun onRefresh() {
        refreshEvent.tryEmit(Unit)
    }
}