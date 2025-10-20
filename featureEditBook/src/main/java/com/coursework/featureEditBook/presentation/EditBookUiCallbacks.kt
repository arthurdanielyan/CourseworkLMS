package com.coursework.featureEditBook.presentation

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.coursework.corePresentation.commonUi.filters.FilterViewState

@Immutable
internal interface EditBookUiCallbacks {

    fun onBackClick()
    fun onFilePicked(uri: Uri?)
    fun onTitleType(newValue: String)
    fun onSubtitleType(newValue: String)
    fun onAuthorsType(newValue: String)
    fun onPublisherType(newValue: String)
    fun onPublicationYearType(newValue: String)
    fun onEditionType(newValue: String)
    fun onCategorySelected(categoryFilter: FilterViewState)
    fun onLanguageSelected(languageFilter: FilterViewState)
    fun onTotalCopiesType(newValue: String)
    fun onPickCoverImageClick()
    fun onPickPdfClick()
    fun onRemovePdf()
    fun onOpenPdf()
    fun onRemoveImageClick()
    fun onReferenceCheckedStateChanged(checked: Boolean)
    fun onCancelClick()
    fun onPublishClick()
}