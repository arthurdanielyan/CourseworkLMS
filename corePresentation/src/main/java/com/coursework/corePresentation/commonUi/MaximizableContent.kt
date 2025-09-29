package com.coursework.corePresentation.commonUi

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.lerp

@Composable
fun MaximizableContent(
    modifier: Modifier = Modifier,
    isMaximized: Boolean,
    onToggle: (Boolean) -> Unit,
    overlayContent: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var contentAbsoluteOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                contentAbsoluteOffset = it.positionInWindow()
            }
            .clickableWithoutIndication {
                if (isMaximized.not()) {
                    onToggle(true)
                }
            }
    ) {
        content()
    }

    val animProgress = animateFloatAsState(
        targetValue = if (isMaximized) {
            1f
        } else 0f,
        label = "image scale animation progress"
    )
    val isPopupVisible by remember {
        derivedStateOf { animProgress.value > 0f }
    }

    if (isPopupVisible) {
        EdgeToEdgePopup(
            onDismissRequest = {
                onToggle(false)
            }
        ) {
            var contentBounds by remember {
                mutableStateOf(Rect.Zero)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(animProgress.value) {
                        detectTapGestures { offset ->
                            // If tap is outside the content bounds
                            if (!contentBounds.contains(offset)) {
                                onToggle(false)
                            }
                        }
                    }
            ) parentBoxScope@{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            alpha = lerp(0f, 0.7f, animProgress.value)
                        }
                        .background(Color.Black)
                )
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = contentAbsoluteOffset.x.toInt(),
                                y = contentAbsoluteOffset.y.toInt(),
                            )
                        }
                        .maximizable(
                            animProgress = animProgress
                        )
                        .wrapContentSize()
                        .onGloballyPositioned {
                            contentBounds = it.boundsInParent()
                        }
                ) {
                    content()
                }
                if (overlayContent != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {

                                translationX = lerp(
                                    start = contentAbsoluteOffset.x - size.width * 0.5f,
                                    stop = 0f,
                                    fraction = animProgress.value
                                )
                                translationY = lerp(
                                    start = contentAbsoluteOffset.y - size.height * 0.5f,
                                    stop = 0f,
                                    fraction = animProgress.value
                                )
                                alpha = animProgress.value
                                scaleX = animProgress.value
                                scaleY = animProgress.value
                            }
                    ) {
                        this@parentBoxScope.overlayContent()
                    }
                }
            }
        }
    }
}
