package com.coursework.corePresentation.commonUi.filters

import androidx.compose.runtime.Immutable

@Immutable
data class FilterViewState(
    val id: Int,
    val displayName: String,
    val isSelected: Boolean,
)
