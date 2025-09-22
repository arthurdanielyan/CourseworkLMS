package com.coursework.corePresentation.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return remember(this, other) {
        val thisStart = this.calculateStartPadding(layoutDirection)
        val thisEnd = this.calculateEndPadding(layoutDirection)
        val otherStart = other.calculateStartPadding(layoutDirection)
        val otherEnd = other.calculateEndPadding(layoutDirection)
        PaddingValues(
            start = thisStart + otherStart,
            end = thisEnd + otherEnd,
            top = this.calculateTopPadding() + other.calculateTopPadding(),
            bottom = this.calculateBottomPadding() + other.calculateBottomPadding(),
        )
    }
}