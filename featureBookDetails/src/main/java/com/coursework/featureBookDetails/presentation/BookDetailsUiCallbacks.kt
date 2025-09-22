package com.coursework.featureBookDetails.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface BookDetailsUiCallbacks {

    fun onBackClick()
    fun onDownloadPdfClick()
}