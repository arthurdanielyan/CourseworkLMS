package com.coursework.domain.usecases

import com.coursework.domain.model.books.BookDetails
import com.coursework.domain.repository.BooksRepository

class GetBookDetailsUseCase(
    private val repository: BooksRepository
) {

    suspend operator fun invoke(bookId: Long): Result<BookDetails> {
        return repository.getBookDetails(bookId)
    }
}