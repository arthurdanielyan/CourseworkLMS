package com.coursework.featureSearchBooks.booksList.mapper

import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.model.Book
import com.coursework.featureSearchBooks.booksList.viewState.BookViewState
import com.coursework.utils.Mapper

internal class BookViewStateMapper : Mapper<Book, BookViewState> {

    override fun map(from: Book): BookViewState {
        return BookViewState(
            id = from.id,
            title = from.title,
            subtitle = from.subtitle,
            authors = from.authors.toComposeList(),
            publisher = from.publisher,
            hasPdfVersion = from.hasPdfVersion,
        )
    }
}