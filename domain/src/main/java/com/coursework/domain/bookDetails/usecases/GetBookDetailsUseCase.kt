package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.BookDetails

class GetBookDetailsUseCase(
    private val repository: BookDetailsRepository
) {

    suspend operator fun invoke(bookId: Long): Result<BookDetails> {
        return repository.getBookDetails(bookId)
    }
}