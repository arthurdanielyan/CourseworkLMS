package com.coursework.featureBookDetails.presentation.mapper

import com.coursework.domain.model.BookDetails
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsUiModel
import com.coursework.utils.Mapper

internal class BookDetailsViewStateMapper : Mapper<BookDetails, BookDetailsUiModel> {

    override fun map(from: BookDetails): BookDetailsUiModel {
        return BookDetailsUiModel(
            id = from.id,
            title = from.title,
            subtitle = from.subtitle,
            authors = from.authors,
            publisher = from.publisher,
            publicationYear = from.publicationYear,
            edition = from.edition,
            categories = from.categories,
            hasPdfVersion = from.hasPdfVersion,
            pdfUrl = from.pdfUrl,
            coverImageUrl = from.coverImageUrl,
            totalCopies = from.totalCopies,
            copiesAvailable = from.copiesAvailable,
            language = from.language,
            isReferenceOnly = from.isReferenceOnly,
        )
    }
}