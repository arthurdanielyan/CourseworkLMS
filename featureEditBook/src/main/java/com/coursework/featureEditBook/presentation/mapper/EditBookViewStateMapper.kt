package com.coursework.featureEditBook.presentation.mapper

import com.coursework.domain.model.BookDetails
import com.coursework.featureEditBook.presentation.EditBookDetailsViewState
import com.coursework.utils.Mapper

class EditBookViewStateMapper : Mapper<BookDetails, EditBookDetailsViewState> {

    override fun map(from: BookDetails): EditBookDetailsViewState {
        return EditBookDetailsViewState(
            titleInput = from.title,
            subtitleInput = from.subtitle.toString(),
            authorsInput = from.authors.joinToString(),
            publisherInput = from.publisher.toString(),
            publicationYearInput = from.publicationYear.toString(),
            editionInput = from.edition.orEmpty(),
            categoriesInput = from.categories.joinToString(),
            pdfUrlInput = from.pdfUrl.toString(),
            coverImageUrlInput = from.coverImageUrl.toString(),
            totalCopiesInput = from.totalCopies.toString(),
            copiesAvailableInput = from.copiesAvailable.toString(),
            languageInput = from.language,
            isReferenceOnlyChecked = from.isReferenceOnly,
        )
    }
}