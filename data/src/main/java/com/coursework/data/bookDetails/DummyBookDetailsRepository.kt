package com.coursework.data.bookDetails

import com.coursework.data.MockData
import com.coursework.data.bookDetails.mapper.NamedItemResponseMapper
import com.coursework.data.bookDetails.mapper.ReserveBookResponseMapper
import com.coursework.data.bookDetails.model.ReserveBookResponse
import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.domain.bookDetails.model.ReserveBook
import com.coursework.utils.mapList
import kotlinx.datetime.LocalDate

class DummyBookDetailsRepository(
    private val namedItemResponseMapper: NamedItemResponseMapper,
    private val reserveBookResponseMapper: ReserveBookResponseMapper,
) : BookDetailsRepository {

    override suspend fun getCategories(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Categories)
        }
    }

    override suspend fun getLanguages(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Languages)
        }
    }

    override suspend fun getTeachers(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Teachers)
        }
    }

    override suspend fun getMaxReservationDate(bookId: Long): Result<LocalDate> {
        return runCatching { // Mock implementation
            LocalDate(2026, 2, 15)
        }
    }

    override suspend fun reserveBook(bookId: Long, returnDate: LocalDate): Result<ReserveBook> {
        return runCatching { // Mock implementation
            reserveBookResponseMapper(
                ReserveBookResponse(
                    bookId = 1,
                    isSuccess = true,
                    displayMessage = "Your reservation has been placed. You have 24 hours to take the book. Return the book by 15 Feb 2026"
                )
            )
        }
    }

    override suspend fun getBookDetails(bookId: Long): Result<BookDetails> {
        return runCatching {
            MockData.bookDetails.first { it.id == bookId }
        }
    }
}