package com.coursework.featureEditBook.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.systemUtils.externalActivityLauncher.ExternalActivityLauncher
import com.coursework.corePresentation.systemUtils.uriResolver.UriResolver
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.domain.bookDetails.usecases.GetBookDetailsUseCase
import com.coursework.domain.bookDetails.usecases.GetCategories
import com.coursework.domain.bookDetails.usecases.GetLanguages
import com.coursework.featureEditBook.presentation.mapper.EditBookViewStateMapper
import com.coursework.featureEditBook.presentation.mapper.FilterViewStateMapper
import com.coursework.featureEditBook.presentation.viewState.BookPdfViewState
import com.coursework.featureEditBook.presentation.viewState.CoverImageViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookDetailsViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookViewState
import com.coursework.utils.mapList
import com.coursework.utils.stateInWhileSubscribed
import com.coursework.utils.stringProvider.StringProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.coursework.corePresentation.R.string as CoreStrings
import com.coursework.featureEditBook.R.string as Strings

internal class EditBookViewModel(
    destination: EditBookDestination,
    private val appRouter: AppRouter,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val getCategories: GetCategories,
    private val getLanguages: GetLanguages,
    private val filterViewStateMapper: FilterViewStateMapper,
    private val editBookViewStateMapper: EditBookViewStateMapper,
    private val uriResolver: UriResolver,
    private val externalActivityLauncher: ExternalActivityLauncher,
    private val stringProvider: StringProvider,
) : ViewModel(), EditBookUiCallbacks {

    private val details = MutableStateFlow(EditBookDetailsViewState())
    private val topBarTitle = MutableStateFlow("")
    private val dataLoadingState = MutableStateFlow(DataLoadingState.Loading)
    val uiState = combine(
        topBarTitle,
        details,
        dataLoadingState,
    ) { topBarTitle, details, dataLoadingState ->

        EditBookViewState(
            topBarTitle = topBarTitle,
            details = details,
            dataLoadingState = dataLoadingState,
        )
    }.stateInWhileSubscribed(viewModelScope, EditBookViewState())

    private val _uiEffect = Channel<EditBookUiEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val uiEffect = _uiEffect.receiveAsFlow()

    private var coverImageUri: Uri? = null // will be used to send to backend
    private var pdfUri: Uri? = null // will be used to send to backend
    private var filePickerCaller: SelectorCaller? = null

    init {
        if (destination.isNewBook) {
            dataLoadingState.update { DataLoadingState.Success }
            topBarTitle.update { stringProvider.string(Strings.add_book) }
        } else {
            destination.bookId?.let(::getBookDetails)
        }
        setupFilters()
    }

    private fun getBookDetails(bookId: Long) {
        viewModelScope.launch {
            dataLoadingState.update { DataLoadingState.Loading }
            delay(1000) // simulate loading
            getBookDetailsUseCase(bookId)
                .onSuccess { result ->
                    details.update {
                        editBookViewStateMapper.map(result)
                    }
                    topBarTitle.update { result.title }
//                    dataLoadingState.update { DataLoadingState.Success }
                }
                .onFailure {
//                    dataLoadingState.update { DataLoadingState.Error }
                }
        }
    }

    private fun setupFilters() {
        viewModelScope.launch {
            runCatching {
                getCategories()
                    .onSuccess { categories ->
                        details.update { oldState ->
                            oldState.copy(
                                categories = filterViewStateMapper.mapList(
                                    list = categories,
                                    params = { category ->
                                        FilterViewStateMapper.Params(
                                            isSelected = details.value.categories
                                                .firstOrNull { it.id == category.id } != null
                                        )
                                    }
                                ).toComposeList()
                            )
                        }
                    }.getOrThrow()
                getLanguages()
                    .onSuccess { languages ->
                        details.update { oldState ->
                            oldState.copy(
                                languages = filterViewStateMapper.mapList(
                                    list = languages,
                                    params = { language ->
                                        FilterViewStateMapper.Params(
                                            isSelected = details.value.languages
                                                .firstOrNull { it.id == language.id } != null
                                        )
                                    }
                                ).toComposeList()
                            )
                        }
                    }.getOrThrow()
            }.onSuccess {
                dataLoadingState.update { DataLoadingState.Success }
            }.onFailure { throwable ->
                dataLoadingState.update { throwable.toDataLoadingState() }
            }
        }
    }

    override fun onBackClick() {
        appRouter.pop()
    }

    override fun onFilePicked(uri: Uri?) {
        if (uri == null) return
        when (filePickerCaller) {
            SelectorCaller.PdfPicker -> {
                pdfUri = uri
                details.update {
                    it.copy(
                        bookPdf = BookPdfViewState(
                            pdfUrl = null,
                            localFileSelected = true,
                            displayName = uriResolver.getFileName(uri)
                        )
                    )
                }
            }

            SelectorCaller.CoverImagePicker -> {
                coverImageUri = uri
                details.update {
                    it.copy(
                        coverImage = CoverImageViewState(
                            url = null,
                            localUri = uri.toString()
                        )
                    )
                }
            }

            null -> return
        }
    }

    override fun onTitleType(newValue: String) {
        details.update {
            it.copy(titleInput = newValue)
        }
    }

    override fun onSubtitleType(newValue: String) {
        details.update {
            it.copy(subtitleInput = newValue)
        }
    }

    override fun onAuthorsType(newValue: String) {
        details.update {
            it.copy(authorsInput = newValue)
        }
    }

    override fun onPublisherType(newValue: String) {
        details.update {
            it.copy(publisherInput = newValue)
        }
    }

    override fun onPublicationYearType(newValue: String) {
        details.update {
            it.copy(publicationYearInput = newValue)
        }
    }

    override fun onEditionType(newValue: String) {
        details.update {
            it.copy(editionInput = newValue)
        }
    }

    override fun onCategorySelected(categoryFilter: FilterViewState) {
        details.update {
            it.copy(
                categories = it.categories.map { filter ->
                    if (filter.id == categoryFilter.id) {
                        filter.copy(isSelected = filter.isSelected.not())
                    } else {
                        filter
                    }
                }.toComposeList()
            )
        }
    }

    override fun onLanguageSelected(languageFilter: FilterViewState) {
        details.update {
            it.copy(
                languages = it.languages.map { filter ->
                    filter.copy(isSelected = filter.id == languageFilter.id)
                }.toComposeList()
            )
        }
    }

    override fun onTotalCopiesType(newValue: String) {
        details.update {
            it.copy(totalCopiesInput = newValue)
        }
    }

    override fun onPickCoverImageClick() {
        filePickerCaller = SelectorCaller.CoverImagePicker
        _uiEffect.trySend(EditBookUiEffect.PickCoverImage)
    }

    override fun onPickPdfClick() {
        filePickerCaller = SelectorCaller.PdfPicker
        _uiEffect.trySend(EditBookUiEffect.PickPdf)
    }

    override fun onRemovePdf() {
        pdfUri = null
        details.update {
            it.copy(
                bookPdf = BookPdfViewState()
            )
        }
    }

    override fun onOpenPdf() {
        pdfUri?.let {
            when (externalActivityLauncher.launchPdfViewer(it)) {
                ExternalActivityLauncher.ResultCodes.UnknownError -> {
                    _uiEffect.trySend(
                        EditBookUiEffect.ShowError(
                            stringProvider.string(CoreStrings.unknown_error_occurred)
                        )
                    )
                }

                ExternalActivityLauncher.ResultCodes.NoActivityFound -> {
                    _uiEffect.trySend(
                        EditBookUiEffect.ShowError(
                            stringProvider.string(Strings.no_pdf_viewer_app)
                        )
                    )
                }
            }
        }
    }

    override fun onRemoveImageClick() {
        coverImageUri = null
        details.update {
            it.copy(
                coverImage = CoverImageViewState()
            )
        }
    }

    override fun onReferenceCheckedStateChanged(checked: Boolean) {
        details.update {
            it.copy(
                isReferenceOnlyChecked = checked
            )
        }
    }

    override fun onCancelClick() {
        appRouter.pop()
    }

    override fun onPublishClick() {
        // TODO: Not yet implemented
    }
}