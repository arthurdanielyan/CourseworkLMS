package com.coursework.featureEditBook.presentation

sealed interface EditBookUiEffect {

    object PickCoverImage : EditBookUiEffect
    object PickPdf : EditBookUiEffect

    class ShowError(
        val message: String,
    ) : EditBookUiEffect
}