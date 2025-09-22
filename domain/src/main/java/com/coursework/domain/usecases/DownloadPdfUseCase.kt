package com.coursework.domain.usecases

import com.coursework.domain.model.BookDetails
import com.coursework.domain.repository.BooksRepository

class DownloadPdfUseCase(
    val repository: BooksRepository
) {

    operator fun invoke(book: BookDetails) {
        repository.downloadPdf(book)
    }
}