package com.coursework.corePresentation.commonUi.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.alpha
import com.coursework.corePresentation.commonUi.autoSizeText.AutoSizeText

@Composable
fun TopBarWithBackButton(
    modifier: Modifier = Modifier,
    alpha: () -> Float,
    onBackClick: () -> Unit,
    title: String,
) {
    val isVisible by remember {
        derivedStateOf {
            alpha() > 0f
        }
    }
    if (isVisible) {
        Box(
            modifier = modifier
                .alpha {
                    alpha()
                }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .requiredHeight(TopBarHeight)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TopBarBackButton(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    onClick = onBackClick,
                )
                AutoSizeText(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    text = title,
                    forceFit = true,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

val TopBarHeight = 64.dp
