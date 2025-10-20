package com.coursework.domain.books.usecases

import com.coursework.domain.books.BooksRepository
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.SearchFilters
import com.coursework.domain.books.model.books.BookPaginationResult

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