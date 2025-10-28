package com.coursework.domain.bookDetails

import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.domain.bookDetails.model.ReserveBook
import kotlinx.datetime.LocalDate

interface BookDetailsRepository {

    suspend fun getCategories(): Result<List<NamedItem>>

    suspend fun getLanguages(): Result<List<NamedItem>>

    suspend fun getTeachers(): Result<List<NamedItem>>

    suspend fun getMaxReservationDate(bookId: Long): Result<LocalDate>

    suspend fun reserveBook(bookId: Long, returnDate: LocalDate): Result<ReserveBook>

    suspend fun getBookDetails(bookId: Long): Result<BookDetails>
}