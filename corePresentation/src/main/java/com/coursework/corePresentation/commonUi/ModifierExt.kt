package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.lerp
import kotlin.math.min

fun Modifier.alpha(alpha: () -> Float) =
    this.graphicsLayer {
        this.alpha = alpha()
    }

fun Modifier.modifyIf(condition: Boolean, transform: Modifier.() -> Modifier) =
    this.then(
        if (condition) {
            this.transform()
        } else {
            Modifier
        }
    )

fun <T> Modifier.modifyIfNotNull(
    subject: T?,
    otherwise: (Modifier.() -> Modifier)? = null,
    transform: Modifier.(T) -> Modifier
) =
    this.then(
        if (subject != null) {
            this.transform(subject)
        } else {
            otherwise?.let {
                this.it()
            } ?: Modifier
        }
    )

fun Modifier.clickableWithoutIndication(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    this.clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = onClick,
    )
}

fun Modifier.maximizable(
    animProgress: State<Float>,
): Modifier = composed {

    val windowInfo = LocalWindowInfo.current
    val screenWidth = remember(windowInfo) {
        windowInfo.containerSize.width
    }
    val screenHeight = remember(windowInfo) {
        windowInfo.containerSize.height
    }

    var contentSize by remember {
        mutableStateOf(IntSize(1, 1))
    }
    var position by remember {
        mutableStateOf(Offset(1f, 1f))
    }

    this
        .onGloballyPositioned {
            contentSize = it.size
            position = it.positionInWindow()
        }
        .graphicsLayer {
            val scaleXFactor = screenWidth / contentSize.width.toFloat()
            val scaleYFactor = screenHeight / contentSize.height.toFloat()
            val scale = min(scaleXFactor, scaleYFactor)

            scaleX = lerp(
                start = 1f,
                stop = scale,
                fraction = animProgress.value
            )

            scaleY = lerp(
                start = 1f,
                stop = scale,
                fraction = animProgress.value
            )

            translationX = lerp(
                start = 0f,
                stop = screenWidth * 0.5f - position.x - contentSize.width * 0.5f,
                fraction = animProgress.value
            )
            translationY = lerp(
                start = 0f,
                stop = screenHeight * 0.5f - position.y - contentSize.height * 0.5f,
                fraction = animProgress.value
            )
        }
}
