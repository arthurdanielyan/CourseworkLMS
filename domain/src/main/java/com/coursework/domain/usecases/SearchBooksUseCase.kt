package com.coursework.domain.usecases

import com.coursework.domain.model.Book
import com.coursework.domain.repository.BooksRepository

class SearchBooksUseCase(
    private val repository: BooksRepository
) {

    suspend operator fun invoke(query: String): Result<List<Book>> {
        return repository.getBooks(query)
    }
}