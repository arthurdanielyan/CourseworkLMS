package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContentWithFab(
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        content()
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            floatingActionButton()
        }
    }
}