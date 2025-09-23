package com.coursework.featureEditBook.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.domain.usecases.GetBookDetailsUseCase
import com.coursework.featureEditBook.EditBookDestination
import com.coursework.featureEditBook.presentation.mapper.EditBookViewStateMapper
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditBookViewModel(
    destination: EditBookDestination,
    private val appRouter: AppRouter,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val editBookViewStateMapper: EditBookViewStateMapper,
) : ViewModel(), EditBookUiCallbacks {

    private val details = MutableStateFlow(EditBookDetailsViewState())
    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)
    val uiState = combine(
        details,
        dataLoadingState,
    ) { details, dataLoadingState ->

        EditBookViewState(
            details = details,
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, EditBookViewState())

    init {
        if (destination.isNewBook) {
            dataLoadingState.update { DataLoadingState.Success }
        } else {
            destination.bookId?.let(::getBookDetails)
        }
    }

    private fun getBookDetails(bookId: Long) {
        viewModelScope.launch {
            dataLoadingState.update { DataLoadingState.Loading }
            delay(1000) // simulate loading
            getBookDetailsUseCase(bookId)
                .onSuccess { result ->
                    details.update { editBookViewStateMapper.map(result) }
                    dataLoadingState.update { DataLoadingState.Success }
                }
                .onFailure {
                    dataLoadingState.update { DataLoadingState.Error }
                }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }
}