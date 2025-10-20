package com.coursework.featureEditBook.presentation.mapper

import com.coursework.corePresentation.commonUi.filters.FilterViewState
import com.coursework.domain.bookDetails.model.NamedItem
import com.coursework.featureEditBook.presentation.mapper.FilterViewStateMapper.Params
import com.coursework.utils.ParameterizedMapper

class FilterViewStateMapper : ParameterizedMapper<NamedItem, Params, FilterViewState> {

    data class Params(
        val isSelected: Boolean,
    )

    override fun map(from: NamedItem, params: Params): FilterViewState {
        return FilterViewState(
            id = from.id,
            displayName = from.displayName,
            isSelected = params.isSelected
        )
    }
}