package com.coursework.domain.repository

import com.coursework.domain.model.Book
import com.coursework.domain.model.BookDetails

interface BooksRepository {

    suspend fun getBooks(query: String): Result<List<Book>>

    suspend fun getBookDetails(bookId: Long): Result<BookDetails>

    fun downloadPdf(book: BookDetails): Result<Unit>
}