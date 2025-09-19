package com.coursework.featureSearchBooks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coursework.corePresentation.commonUi.LoadingStatePresenter
import com.coursework.corePresentation.commonUi.TextField
import com.coursework.corePresentation.viewState.ComposeList
import com.coursework.featureSearchBooks.presentation.BookViewState
import com.coursework.featureSearchBooks.presentation.SearchBooksUiCallbacks
import com.coursework.featureSearchBooks.presentation.SearchBooksViewModel
import com.coursework.featureSearchBooks.presentation.SearchBooksViewState
import org.koin.compose.koinInject
import com.coursework.featureSearchBooks.R.string as Strings

@Composable
fun SearchBooksScreen() {
    val viewModel = koinInject<SearchBooksViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SearchBooksScreen(
        state = state,
        callbacks = viewModel
    )
}

@Composable
private fun SearchBooksScreen(
    state: SearchBooksViewState,
    callbacks: SearchBooksUiCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) {
        TextField(
            value = state.searchInput,
            onValueChange = callbacks::onSearchQueryType,
            label = stringResource(Strings.search_books),
            showCleanIcon = true
        )


        LoadingStatePresenter(
            modifier = Modifier
                .fillMaxSize(),
            dataLoadingState = state.dataLoadingState,
            onRefresh = callbacks::onRefresh,
        ) {
            BooksList(
                books = state.books,
                onBookClick = callbacks::onBookClick
            )
        }
    }
}

@Composable
private fun BooksList(
    books: ComposeList<BookViewState>,
    onBookClick: (BookViewState) -> Unit,
) {
    if (books.isEmpty()) {
        NoBooksFoundView()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(books) { book ->
                BookItem(
                    book = book,
                    onClick = {
                        onBookClick(book)
                    }
                )
            }
        }
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
