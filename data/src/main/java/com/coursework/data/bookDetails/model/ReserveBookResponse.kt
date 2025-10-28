package com.coursework.data.bookDetails.model

import kotlinx.serialization.Serializable

@Serializable
data class ReserveBookResponse(
    val bookId: Long?,
    val isSuccess: Boolean?,
    val displayMessage: String?
)