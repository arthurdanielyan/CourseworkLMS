package com.coursework.domain.model.books

data class BookPaginationResult(
    val books: List<Book>,
    val isEndReached: Boolean,
)
