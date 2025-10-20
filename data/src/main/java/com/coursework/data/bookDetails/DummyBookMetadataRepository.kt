package com.coursework.data.bookDetails

import com.coursework.data.MockData
import com.coursework.data.bookDetails.mapper.NamedItemResponseMapper
import com.coursework.domain.bookDetails.BookMetadataRepository
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.utils.mapList

class DummyBookMetadataRepository(
    private val namedItemResponseMapper: NamedItemResponseMapper,
) : BookMetadataRepository {

    override suspend fun getCategories(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Categories)
        }
    }

    override suspend fun getLanguages(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Languages)
        }
    }

    override suspend fun getTeachers(): Result<List<NamedItem>> {
        return runCatching {
            namedItemResponseMapper.mapList(MockData.Teachers)
        }
    }
}