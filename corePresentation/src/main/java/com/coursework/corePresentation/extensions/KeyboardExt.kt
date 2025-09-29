package com.coursework.corePresentation.extensions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity

@Composable
fun isKeyboardVisible(): State<Boolean> {
    val keyboardHeight = LocalDensity.current
        .run {
            WindowInsets.ime.getBottom(this)
        }
    return rememberUpdatedState(keyboardHeight > 0)
}