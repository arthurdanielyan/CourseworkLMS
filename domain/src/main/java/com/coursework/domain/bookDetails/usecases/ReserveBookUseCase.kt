package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.ReserveBook
import kotlinx.datetime.LocalDate

class ReserveBookUseCase(
    val repository: BookDetailsRepository,
) {

    suspend operator fun invoke(bookId: Long, returnDate: LocalDate): Result<ReserveBook> {
        return repository.reserveBook(bookId, returnDate)
    }
}