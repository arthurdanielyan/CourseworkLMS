package com.coursework.featureSearchBooks.booksList.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.coursework.corePresentation.commonUi.ContentWithFab
import com.coursework.corePresentation.commonUi.IconButton
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.PrimaryButton
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.extensions.ComposeCollect
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresentation.viewState.toDataLoadingState
import com.coursework.featureSearchBooks.booksList.BooksListUiCallbacks
import com.coursework.featureSearchBooks.booksList.BooksListViewModel
import com.coursework.featureSearchBooks.booksList.viewState.BookViewState
import com.coursework.featureSearchBooks.booksList.viewState.BooksListViewState
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import org.koin.androidx.compose.koinViewModel
import com.coursework.corePresentation.R.drawable as CoreDrawables
import com.coursework.featureSearchBooks.R.drawable as Drawables
import com.coursework.featureSearchBooks.R.string as Strings

@Composable
fun SearchBooksScreen(
    sharedViewModel: SearchBooksSharedViewModel,
) {
    val viewModel = koinViewModel<BooksListViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val books = viewModel.booksPagingSource.collectAsLazyPagingItems()

    ComposeCollect(sharedViewModel.searchFilters) {
        viewModel.onGetFilterResult(it)
    }

    SearchBooksScreen(
        state = state,
        booksPagingItems = books,
        callbacks = viewModel
    )
}

@Composable
private fun SearchBooksScreen(
    state: BooksListViewState,
    booksPagingItems: LazyPagingItems<BookViewState>,
    callbacks: BooksListUiCallbacks
) {
    var additionalActionsExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    ContentWithFab(
        floatingActionButton = {
            if (state.showAddBookButton) {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape
                        ),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add a book",
                    iconSize = 36.dp,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    onClick = callbacks::onAddBookClick,
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .statusBarsPadding(),
        ) {
            TextField(
                value = state.searchInput,
                onValueChange = callbacks::onSearchQueryType,
                label = stringResource(Strings.search_books),
                leadingIcon = {
                    SearchFieldLeadingContent(
                        isAdditionalIconsExpanded = additionalActionsExpanded,
                        onAdditionalActionsToggle = {
                            additionalActionsExpanded = it
                        },
                        onLogoutClick = {
                            additionalActionsExpanded = false
                            callbacks.onLogoutClick()
                        }
                    )
                },
                trailingIcon = {
                    SearchFieldTrailingContent(
                        onClearQueryClick = { callbacks.onSearchQueryType("") },
                        onSearchFiltersClick = callbacks::onSearchFiltersClick
                    )
                },
            )

            val initialDataLoadingState by booksPagingItems.initialDataLoadingState()
            LoadingStatePresenter(
                modifier = Modifier
                    .fillMaxSize(),
                dataLoadingState = initialDataLoadingState,
                onRefresh = callbacks::onRefresh,
            ) {
                BooksList(
                    booksPagingItems = booksPagingItems,
                    onBookClick = callbacks::onBookClick
                )
            }
        }
    }
}

@Composable
private fun SearchFieldTrailingContent(
    onClearQueryClick: () -> Unit,
    onSearchFiltersClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            imageVector = ImageVector.vectorResource(CoreDrawables.ic_close),
            onClick = onClearQueryClick,
            contentDescription = "Clear input"
        )
        IconButton(
            imageVector = ImageVector.vectorResource(Drawables.ic_options),
            onClick = onSearchFiltersClick,
            contentDescription = "Search filters"
        )
    }
}

@Composable
private fun SearchFieldLeadingContent(
    isAdditionalIconsExpanded: Boolean,
    onAdditionalActionsToggle: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
) {
    IconButton(
        imageVector = ImageVector.vectorResource(CoreDrawables.ic_more),
        contentDescription = "Additional actions dropdown",
        onClick = { onAdditionalActionsToggle(true) }
    )
    DropdownMenu(
        offset = DpOffset(x = 8.dp, y = 0.dp),
        expanded = isAdditionalIconsExpanded,
        onDismissRequest = { onAdditionalActionsToggle(false) }
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(Strings.logout),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Logout"
                )
            },
            onClick = onLogoutClick
        )
    }
}

@Composable
private fun BooksList(
    booksPagingItems: LazyPagingItems<BookViewState>,
    onBookClick: (BookViewState) -> Unit,
) {
    val isEmpty by remember(booksPagingItems.loadState.refresh, booksPagingItems.itemSnapshotList) {
        mutableStateOf(
            booksPagingItems.loadState.refresh is LoadState.NotLoading &&
                    booksPagingItems.itemSnapshotList.isEmpty()
        )
    }
    if (isEmpty) {
        NoBooksFoundView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            contentPadding = PaddingValues(
                bottom = 16.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(booksPagingItems.itemCount) { index ->
                val book = booksPagingItems[index] ?: return@items
                BookItem(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    }
                )
            }

            booksListBottomContent(
                booksPagingItems = booksPagingItems
            )
        }
    }
}

private fun LazyListScope.booksListBottomContent(
    booksPagingItems: LazyPagingItems<BookViewState>
) {
    when (booksPagingItems.loadState.append) {
        is LoadState.Loading ->
            item {
                CircularProgressIndicator()
            }

        is LoadState.Error ->
            item {
                PrimaryButton(
                    text = stringResource(Strings.retry),
                    onClick = booksPagingItems::retry
                )
            }

        else -> Unit
    }
}

@Composable
private fun NoBooksFoundView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Strings.no_books_found),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.initialDataLoadingState(): State<DataLoadingState> {
    return remember {
        derivedStateOf {
            when (loadState.refresh) {
                is LoadState.Loading -> DataLoadingState.Loading
                is LoadState.Error -> (loadState.refresh as LoadState.Error).error.toDataLoadingState()
                else -> DataLoadingState.Success
            }
        }
    }
}
