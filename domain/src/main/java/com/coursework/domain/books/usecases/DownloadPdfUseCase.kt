package com.coursework.domain.books.usecases

import com.coursework.domain.bookDetails.model.BookDetails
import com.coursework.domain.books.BooksRepository

class DownloadPdfUseCase(
    val repository: BooksRepository
) {

    operator fun invoke(book: BookDetails) {
        repository.downloadPdf(book)
    }
}