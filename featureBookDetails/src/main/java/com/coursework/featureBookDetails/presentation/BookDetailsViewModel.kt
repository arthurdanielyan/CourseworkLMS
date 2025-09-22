package com.coursework.featureBookDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.domain.usecases.GetBookDetails
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.presentation.mapper.BookDetailsViewStateMapper
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsScreenViewState
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsViewState
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class BookDetailsViewModel(
    private val destination: BookDetailsDestination,
    private val appRouter: AppRouter,
    private val getBookDetails: GetBookDetails,
    private val bookDetailsViewStateMapper: BookDetailsViewStateMapper,
) : ViewModel(), BookDetailsUiCallbacks {

    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)
    private val bookDetails = MutableStateFlow<BookDetailsViewState?>(null)

    val uiState = combine(
        dataLoadingState,
        bookDetails,
    ) { dataLoadingState, bookDetails ->

        BookDetailsScreenViewState(
            dataLoadingState = dataLoadingState,
            bookDetails = bookDetails,
        )
    }.stateInWhileSubscribed(viewModelScope, BookDetailsScreenViewState())

    init {
        getBookDetails()
    }

    private fun getBookDetails() {
        viewModelScope.launch {
            getBookDetails(destination.id)
                .onSuccess { result ->
                    dataLoadingState.value = DataLoadingState.Success
                    bookDetails.update {
                        bookDetailsViewStateMapper.map(result)
                    }
                }
                .onFailure {
                    dataLoadingState.value = DataLoadingState.Error
                }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }
}