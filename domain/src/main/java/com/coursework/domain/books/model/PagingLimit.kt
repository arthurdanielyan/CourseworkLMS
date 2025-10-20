package com.coursework.domain.books.model

data class PagingLimit(
    val limit: Int = 20,
    val offset: Int = 0,
)
