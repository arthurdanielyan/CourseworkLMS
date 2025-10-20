package com.coursework.featureEditBook.presentation.mapper

import com.coursework.corePresentation.viewState.toComposeList
import com.coursework.domain.books.model.books.BookDetails
import com.coursework.featureEditBook.presentation.viewState.BookPdfViewState
import com.coursework.featureEditBook.presentation.viewState.CoverImageViewState
import com.coursework.featureEditBook.presentation.viewState.EditBookDetailsViewState
import com.coursework.utils.Mapper
import com.coursework.utils.mapList

internal class EditBookViewStateMapper(
    private val filterViewStateMapper: FilterViewStateMapper,
) : Mapper<BookDetails, EditBookDetailsViewState> {

    override fun map(from: BookDetails): EditBookDetailsViewState {
        return EditBookDetailsViewState(
            titleInput = from.title,
            subtitleInput = from.subtitle.toString(),
            authorsInput = from.authors.joinToString(),
            publisherInput = from.publisher.toString(),
            publicationYearInput = from.publicationYear.toString(),
            editionInput = from.edition.orEmpty(),
            categories = filterViewStateMapper.mapList(
                list = from.categories,
                params = { FilterViewStateMapper.Params(isSelected = true) }
            ).toComposeList(),
            bookPdf = BookPdfViewState(
                pdfUrl = from.pdfUrl.toString(),
                displayName = from.pdfTitle.toString(),
            ),
            coverImage = CoverImageViewState(
                url = from.coverImageUrl.toString(),
            ),
            totalCopiesInput = from.totalCopies.toString(),
            isReferenceOnlyChecked = from.isReferenceOnly,
        )
    }
}