package com.coursework.domain.bookDetails.usecases

import com.coursework.domain.bookDetails.BookDetailsRepository
import com.coursework.domain.bookDetails.model.NamedItem

class GetLanguages(
    private val repository: BookDetailsRepository,
) {

    suspend operator fun invoke(): Result<List<NamedItem>> {
        return repository.getLanguages()
    }
}