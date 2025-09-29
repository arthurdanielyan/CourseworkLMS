package com.coursework.featureEditBook.presentation

import android.net.Uri
import androidx.compose.runtime.Immutable

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
    fun onCategoriesType(newValue: String)
    fun onTotalCopiesType(newValue: String)
    fun onCopiesAvailableType(newValue: String)
    fun onLanguageType(newValue: String)
    fun onPickCoverImageClick()
    fun onPickPdfClick()
    fun onRemovePdf()
    fun onOpenPdf()
    fun onRemoveImageClick()
    fun onCancelClick()
    fun onPublishClick()
}