package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookMetadataRepository
import com.coursework.domain.bookDetails.model.NamedItem

class GetCategories(
    private val repository: BookMetadataRepository,
) {

    suspend operator fun invoke(): Result<List<NamedItem>> {
        return repository.getCategories()
    }
}