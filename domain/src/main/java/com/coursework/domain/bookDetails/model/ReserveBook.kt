package com.coursework.domain.bookDetails.model

data class ReserveBook(
    val bookId: Long,
    val isSuccess: Boolean,
    val displayMessage: String
)