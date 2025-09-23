package com.coursework.featureEditBook

import com.coursework.corePresentation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class EditBookDestination(
    val bookId: Long? = null,
    val isNewBook: Boolean = false,
) : Destination
