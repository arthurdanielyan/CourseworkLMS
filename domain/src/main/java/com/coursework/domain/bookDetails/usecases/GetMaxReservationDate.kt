package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import kotlinx.datetime.LocalDate

class GetMaxReservationDate(
    private val repository: BookDetailsRepository
) {

    suspend operator fun invoke(bookId: Long): Result<LocalDate> {
        return repository.getMaxReservationDate(bookId)
    }
}