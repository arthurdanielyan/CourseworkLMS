package com.coursework.domain.usecases

import com.coursework.domain.model.PagingLimit
import com.coursework.domain.model.SearchFilters
import com.coursework.domain.model.books.BookPaginationResult
import com.coursework.domain.repository.BooksRepository

class GetBooksUseCase(
    private val repository: BooksRepository
) {

    data class Params(
        val query: String,
        val filters: SearchFilters,
        val pagingLimit: PagingLimit,
    )

    suspend operator fun invoke(params: Params): Result<BookPaginationResult> {
        return repository.getBooks(
            query = params.query,
            filters = params.filters,
            pagingLimit = params.pagingLimit,
        )
    }
}