package com.coursework.corePresentation.commonUi.autoSizeText

import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.abs

internal class LinearAutoSizeTextCalculator(
    private val containerSize: IntSize,
    private val initialFontSize: TextUnit,
    private val forceFit: Boolean = true,
    private val textMeasurer: TextMeasurer,
    private val text: String,
    private val style: TextStyle,
    private val maxLines: Int,
    private val softWrap: Boolean,
    density: Density,
) : AutoSizeTextCalculator {

    private companion object {
        private const val SizeMultiplier = 0.1f
        val MinFontSize = 10.sp
        val MaxFontSize = 60.sp
    }

    val minDiff = density.run {
        16.dp.toPx()
    }

    override fun calculateTextSize(): TextUnit {
        var estimatedSize = if (initialFontSize != TextUnit.Unspecified) {
            initialFontSize
        } else {
            style.fontSize
        }

        while (true) {
            if (estimatedSize <= MinFontSize) {
                return MinFontSize
            } else if (estimatedSize >= MaxFontSize) {
                return MaxFontSize
            }
            textMeasurer.measure(
                text = text,
                softWrap = softWrap,
                maxLines = maxLines,
                style = style.copy(
                    fontSize = estimatedSize
                ),
                constraints = Constraints(
                    maxWidth = containerSize.width,
                    maxHeight = containerSize.height,
                )
            ).apply {
                estimatedSize *= if (didOverflowWidth || didOverflowHeight) {
                    (1f - SizeMultiplier)
                } else if (forceFit) {
                    if (
                        abs(this.size.width - containerSize.width) <= minDiff ||
                        abs(this.size.height - containerSize.height) <= minDiff
                    ) {
                        return estimatedSize
                    } else {
                        (1f + SizeMultiplier)
                    }
                } else {
                    return estimatedSize
                }
            }
        }
    }
}
