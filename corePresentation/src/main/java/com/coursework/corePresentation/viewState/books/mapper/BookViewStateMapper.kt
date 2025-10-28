package com.coursework.corePresentation.viewState.books.mapper

import com.coursework.corePresentation.viewState.books.BookViewState
import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.books.model.books.Book
import com.coursework.utils.Mapper

class BookViewStateMapper : Mapper<Book, BookViewState> {

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