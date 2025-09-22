package com.coursework.corePresentation.commonUi.topBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.coursework.corePresentation.commonUi.alpha

@Composable
fun ContentWithTopBarHeader(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: @Composable () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    val scrollState = rememberLazyListState()
    var headerHeight by remember {
        mutableIntStateOf(1)
    }
    var topBarHeight by remember {
        mutableIntStateOf(1)
    }
    val alpha by remember {

        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0) {
                1f - (scrollState.firstVisibleItemScrollOffset).toFloat() / (headerHeight - topBarHeight)
            } else 0f
        }
    }

    Box(
        modifier = modifier
    ) {
        TopBarWithBackButton(
            modifier = Modifier
                .onSizeChanged {
                    topBarHeight = it.height
                }
                .zIndex(1f),
            title = title,
            alpha = {
                1f - alpha
            },
            onBackClick = onBackClick,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            state = scrollState,
        ) {
            item(key = "header_slot") {
                Box(
                    modifier = Modifier
                        .onSizeChanged {
                            headerHeight = it.height
                        }
                        .alpha { alpha },
                ) {
                    header()
                }
            }
            content()
        }
    }
}