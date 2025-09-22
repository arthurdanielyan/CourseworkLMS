package com.coursework.featureBookDetails.ui

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.AsyncImage
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.topBar.ContentWithTopBarHeader
import com.coursework.corePresentation.commonUi.topBar.TopBarBackButton
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.presentation.BookDetailsUiCallbacks
import com.coursework.featureBookDetails.presentation.BookDetailsViewModel
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsScreenViewState
import com.coursework.featureBookDetails.presentation.viewState.BookDetailsViewState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.coursework.featureBookDetails.R.drawable as Drawables
import com.coursework.featureBookDetails.R.string as Strings

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
    state: BookDetailsScreenViewState,
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
    bookDetails: BookDetailsViewState,
    callbacks: BookDetailsUiCallbacks,
) {
    ContentWithTopBarHeader(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        title = bookDetails.title,
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
        bookDetailsContent(
            bookDetails = bookDetails,
            onDownloadPdfClick = callbacks::onDownloadPdfClick
        )
    }
}

private fun LazyListScope.bookDetailsContent(
    bookDetails: BookDetailsViewState,
    onDownloadPdfClick: () -> Unit
) {
    if (bookDetails.hasPdfVersion) {
        item("download") {
            PrimaryButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(Strings.download_pdf),
                onClick = onDownloadPdfClick
            )
        }
    }
    if (bookDetails.isReferenceOnly) {
        item("subtitle") {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(Strings.reference_only_message),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
    if (bookDetails.subtitle?.isNotBlank() == true) {
        item("subtitle") {
            BookDetailBlock(
                title = stringResource(Strings.description),
                detail = bookDetails.subtitle,
            )
        }
    }
    if (bookDetails.authors.isNotEmpty()) {
        item("authors") {
            BookDetailBlock(
                title = stringResource(
                    if (bookDetails.authors.size > 1) Strings.authors
                    else Strings.author
                ),
                detail = bookDetails.authors.joinToString(),
            )
        }
    }
    if (bookDetails.publisher?.isNotBlank() == true) {
        item("published_by") {
            BookDetailBlock(
                title = stringResource(Strings.published_by),
                detail = bookDetails.publisher,
            )
        }
    }
    if (bookDetails.publisher?.isNotBlank() == true) {
        item("publisher") {
            BookDetailBlock(
                title = stringResource(Strings.published_by),
                detail = bookDetails.publisher,
            )
        }
    }
    if (bookDetails.publicationYear != null) {
        item("publication_year") {
            BookDetailBlock(
                title = stringResource(Strings.published_in),
                detail = bookDetails.publicationYear.toString(),
            )
        }
    }
    if (bookDetails.edition?.isNotBlank() == true) {
        item("edition") {
            BookDetailBlock(
                title = stringResource(Strings.edition),
                detail = bookDetails.edition,
            )
        }
    }
    if (bookDetails.categories.isNotEmpty()) {
        item("categories") {
            BookDetailBlock(
                title = stringResource(Strings.categories),
                detail = bookDetails.categories.joinToString(),
            )
        }
    }
    item("total_copies") {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = "${stringResource(Strings.total_copies)} ${bookDetails.totalCopies}",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    item("available_copies") {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = if (bookDetails.copiesAvailable > 0) {
                "${stringResource(Strings.available_copies)} ${bookDetails.totalCopies}"
            } else {
                stringResource(Strings.no_available_copies)
            },
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    item("language") {
        BookDetailBlock(
            title = stringResource(Strings.language),
            detail = bookDetails.language,
        )
    }
}

@Composable
private fun BookDetailBlock(
    title: String,
    detail: String,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = detail,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
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
            placeholder = painterResource(Drawables.book_cover_placeholder),
            error = painterResource(Drawables.book_cover_placeholder),
            contentDescription = null,
            onError = {
                Log.d("yapping", it.result.throwable.toString())
            },
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
