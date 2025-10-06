package com.coursework.corePresentation.navigation.destinations

import com.coursework.corePresentation.Destination
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsDestination(
    val id: Long
) : Destination