package com.coursework.featureBookDetails

import com.coursework.corePresentation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDestination(
    val id: Long
) : Destination