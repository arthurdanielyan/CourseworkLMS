package com.coursework.domain.model

data class Book(
    val id: Long,
    val title: String,
    val subtitle: String?,
    val authors: List<String>,
    val publisher: String?,
    val hasPdfVersion: Boolean,
)