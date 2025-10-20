package com.coursework.domain.bookDetails

import com.coursework.domain.bookDetails.model.NamedItem

interface BookMetadataRepository {

    suspend fun getCategories(): Result<List<NamedItem>>

    suspend fun getLanguages(): Result<List<NamedItem>>

    suspend fun getTeachers(): Result<List<NamedItem>>
}