package com.coursework.domain.usecases

import com.coursework.domain.model.BookDetails
import com.coursework.domain.repository.BooksRepository

class GetBookDetails(
    private val repository: BooksRepository
) {

    suspend operator fun invoke(bookId: Long): Result<BookDetails> {
        return repository.getBookDetails(bookId)
    }
}