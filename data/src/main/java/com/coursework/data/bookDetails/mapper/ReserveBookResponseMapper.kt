package com.coursework.data.bookDetails.mapper

import com.coursework.data.bookDetails.model.ReserveBookResponse
import com.coursework.domain.bookDetails.model.ReserveBook
import com.coursework.utils.Mapper

class ReserveBookResponseMapper : Mapper<ReserveBookResponse, ReserveBook> {

    override fun map(from: ReserveBookResponse): ReserveBook {
        return ReserveBook(
            bookId = from.bookId ?: -1,
            isSuccess = from.isSuccess ?: false,
            displayMessage = from.displayMessage.orEmpty(),
        )
    }
}