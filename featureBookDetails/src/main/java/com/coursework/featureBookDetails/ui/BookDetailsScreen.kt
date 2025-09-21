package com.coursework.featureBookDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.topBar.ContentWithTopBarHeader
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.presentation.BookDetailsUiCallbacks
import com.coursework.featureBookDetails.presentation.BookDetailsViewModel
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsUiModel
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsViewState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun BookDetailsScreen(
    destination: BookDetailsDestination
) {
    val viewModel = koinViewModel<BookDetailsViewModel>(
        parameters = {
            parametersOf(destination)
        }
    )

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: BookDetailsUiCallbacks = viewModel

    BookDetailsScreen(
        state = state,
        callbacks = callbacks,
    )
}

@Composable
private fun BookDetailsScreen(
    state: BookDetailsViewState,
    callbacks: BookDetailsUiCallbacks,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize(),
            dataLoadingState = state.dataLoadingState,
            onRefresh = {}
        ) {
            state.bookDetails?.let {
                BookDetailsContent(
                    bookDetails = it,
                    callbacks = callbacks,
                )
            }
        }
    }
}

@Composable
private fun BookDetailsContent(
    bookDetails: BookDetailsUiModel,
    callbacks: BookDetailsUiCallbacks,
) {
    ContentWithTopBarHeader(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        title = bookDetails.title,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        onBackClick = callbacks::onBackClick,
        header = {
            BookDetailsHeader(
                modifier = Modifier
                    .fillMaxWidth(),
                onBackClick = callbacks::onBackClick,
                coverImageUrl = bookDetails.coverImageUrl,
                bookTitle = bookDetails.title
            )
        }
    ) {
        items(100) {
            Text(
                text = "Book Details Screen, ${bookDetails.title}",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
private fun BookDetailsHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    coverImageUrl: String?,
    bookTitle: String,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val headerHeight = remember(density, windowInfo) {
        density.run {
            (windowInfo.containerSize.height * 0.3f).toDp()
        }
    }
    Box(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        AsyncImage(
            model = coverImageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(headerHeight),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.surface)
                    )
                )
        )
        Text(
            text = bookTitle,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        )
        TopBarBackButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopStart),
            onClick = onBackClick,
        )
    }
}
