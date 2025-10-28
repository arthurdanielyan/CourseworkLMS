package com.coursework.data.books

import com.coursework.data.MockData
import com.coursework.data.downloader.Downloader
import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.books.BooksRepository
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.SearchFilters
import com.coursework.domain.books.model.books.Book
import com.coursework.domain.books.model.books.BookPaginationResult
import kotlinx.coroutines.delay

class DummyBooksRepository(
    val downloader: Downloader,
) : BooksRepository {

    override suspend fun getBooks(
        query: String,
        filters: SearchFilters,
        pagingLimit: PagingLimit,
    ): Result<BookPaginationResult> {
        return runCatching {
            delay(1000)
            val filtered =
                MockData.books.filter { it.title.lowercase().contains(query.lowercase()) }
            BookPaginationResult(
                books = filtered
                    .subList(
                        fromIndex = pagingLimit.offset,
                        toIndex = (pagingLimit.offset + pagingLimit.limit).coerceAtMost(filtered.size)
                    ),
                isEndReached = pagingLimit.offset + pagingLimit.limit >= filtered.size,
            )
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

    override suspend fun getMyAddedBooks(): Result<List<Book>> {
        TODO("Not yet implemented")
    }
}