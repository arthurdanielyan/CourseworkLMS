package com.coursework.domain.books.usecases

import com.coursework.domain.books.BooksRepository
import com.coursework.domain.books.model.books.BookDetails

class DownloadPdfUseCase(
    val repository: BooksRepository
) {

    operator fun invoke(book: BookDetails) {
        repository.downloadPdf(book)
    }
}