package com.coursework.domain.repository

import com.coursework.domain.model.PagingLimit
import com.coursework.domain.model.SearchFilters
import com.coursework.domain.model.books.BookDetails
import com.coursework.domain.model.books.BookPaginationResult

interface BooksRepository {

    suspend fun getBooks(
        query: String,
        filters: SearchFilters,
        pagingLimit: PagingLimit,
    ): Result<BookPaginationResult>

    suspend fun getBookDetails(bookId: Long): Result<BookDetails>

    fun downloadPdf(book: BookDetails): Result<Unit>
}