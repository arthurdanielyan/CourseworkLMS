package com.coursework.featureSearchBooks.booksList

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coursework.domain.books.model.PagingLimit
import com.coursework.domain.books.model.SearchFilters
import com.coursework.domain.books.model.books.Book
import com.coursework.domain.books.usecases.GetBooksUseCase

class BooksPagingSource(
    private val query: String,
    private val filters: SearchFilters,
    private val getBooksUseCase: GetBooksUseCase,
) : PagingSource<Int, Book>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        val offset = params.key ?: 0
        val limit = params.loadSize

        val result = getBooksUseCase(
            GetBooksUseCase.Params(
                query = query,
                filters = filters,
                pagingLimit = PagingLimit(
                    offset = offset,
                    limit = limit,
                )
            )
        )


        return result
            .map {
                val books = it.books
                Log.d("yapping", "offset: $offset, limit: $limit, isEmpty: ${books.isEmpty()}")
                LoadResult.Page(
                    data = books,
                    prevKey = if (offset == 0) null else offset - limit,
                    nextKey = if (it.isEndReached) null else offset + limit
                )
            }.getOrElse {
                Log.d("yapping", it.toString())
                LoadResult.Error(it)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return 0
    }

    class Factory(
        private val getBooksUseCase: GetBooksUseCase
    ) {
        operator fun invoke(
            query: String,
            filters: SearchFilters,
        ): BooksPagingSource {
            return BooksPagingSource(
                query = query,
                filters = filters,
                getBooksUseCase = getBooksUseCase,
            )
        }
    }
}