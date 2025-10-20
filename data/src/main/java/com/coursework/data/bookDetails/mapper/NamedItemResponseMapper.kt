package com.coursework.data.bookDetails.mapper

import com.coursework.data.bookDetails.model.NamedItemResponse
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.utils.Mapper

class NamedItemResponseMapper : Mapper<NamedItemResponse, NamedItem> {

    override fun map(from: NamedItemResponse): NamedItem {
        return NamedItem(
            id = from.id,
            displayName = from.displayName,
        )
    }
}