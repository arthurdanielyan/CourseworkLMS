package com.coursework.domain.books.model.books

data class BookPaginationResult(
    val books: List<Book>,
    val isEndReached: Boolean,
)
