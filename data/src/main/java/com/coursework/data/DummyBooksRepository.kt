package com.coursework.data

import com.coursework.data.downloader.Downloader
import com.coursework.domain.model.Book
import com.coursework.domain.model.BookDetails
import com.coursework.domain.repository.BooksRepository
import kotlinx.coroutines.delay

class DummyBooksRepository(
    val downloader: Downloader,
) : BooksRepository {

    override suspend fun getBooks(query: String): Result<List<Book>> {
        return runCatching {
            delay(1500) // Simulate network delay
            MockData.books.filter {
                it.title.lowercase().contains(query.lowercase())
            }
        }
    }

    override suspend fun getBookDetails(bookId: Long): Result<BookDetails> {
        return runCatching {
            MockData.bookDetails.first { it.id == bookId }
        }
    }

    override fun downloadPdf(book: BookDetails): Result<Unit> {
        return runCatching {
            val pdfUrl = book.pdfUrl ?: throw IllegalArgumentException("Book has no pdf url")
            downloader.downloadFile(
                url = pdfUrl,
                mimeType = "application/pdf",
                title = book.title,
            )
        }
    }
}