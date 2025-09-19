package com.coursework.data

import com.coursework.domain.model.Book
import com.coursework.domain.model.BookDetails
import com.coursework.domain.repository.BooksRepository
import kotlinx.coroutines.delay

class DummyBooksRepository : BooksRepository {

    override suspend fun getBooks(query: String): Result<List<Book>> {
        return runCatching {
            delay(1500) // Simulate network delay
            MockData.books.filter {
                it.title.lowercase().contains(query.lowercase())
            }
        }
    }

    override suspend fun getBookDetails(): Result<BookDetails> {
        return runCatching {
            TODO("Not yet implemented")
        }
    }

    override suspend fun downloadPdf(bookId: Long): Result<Unit> {
        return runCatching {
            TODO("Not yet implemented")
        }
    }
}