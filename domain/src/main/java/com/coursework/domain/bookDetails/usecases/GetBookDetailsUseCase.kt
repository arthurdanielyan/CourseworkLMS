package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.books.BooksRepository
import com.coursework.domain.books.model.books.BookDetails

class GetBookDetailsUseCase(
    private val repository: BooksRepository
) {

    suspend operator fun invoke(bookId: Long): Result<BookDetails> {
        return repository.getBookDetails(bookId)
    }
}