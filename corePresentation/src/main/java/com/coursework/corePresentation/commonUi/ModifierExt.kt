package com.coursework.corePresentation.commonUi

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

fun Modifier.alpha(alpha: () -> Float) =
    this.graphicsLayer {
        this.alpha = alpha()
    }