package com.coursework.domain.model

data class PagingLimit(
    val limit: Int = 20,
    val offset: Int = 0,
)
