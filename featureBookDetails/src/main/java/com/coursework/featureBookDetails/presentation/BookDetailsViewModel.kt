package com.coursework.featureBookDetails.presentation

import androidx.lifecycle.ViewModel
import com.coursework.featureBookDetails.BookDetailsDestination

internal class BookDetailsViewModel(
    private val destination: BookDetailsDestination,
) : ViewModel() {

    val id = destination.id

}