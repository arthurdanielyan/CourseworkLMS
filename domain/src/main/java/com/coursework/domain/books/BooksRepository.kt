package com.coursework.domain.books

import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.SearchFilters
import com.coursework.domain.books.model.books.Book
import com.coursework.domain.books.model.books.BookPaginationResult

interface BooksRepository {

    suspend fun getBooks(
        query: String,
        filters: SearchFilters,
        pagingLimit: PagingLimit,
    ): Result<BookPaginationResult>

    fun downloadPdf(book: BookDetails): Result<Unit>

    suspend fun getMyAddedBooks(): Result<List<Book>>

}