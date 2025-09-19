package com.coursework.corePresentation.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.viewState.DataLoadingState
import com.coursework.corePresenation.R.string as Strings


@Composable
fun LoadingStatePresenter(
    modifier: Modifier = Modifier,
    dataLoadingState: DataLoadingState,
    onRefresh: () -> Unit,
    loadingContent: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    when (dataLoadingState) {
        DataLoadingState.Loading -> {
            if (loadingContent == null) {
                DefaultLoadingView(
                    modifier = modifier
                )
            } else {
                loadingContent()
            }
        }

        DataLoadingState.Error -> {
            ErrorContent(
                modifier = modifier,
                errorMessage = stringResource(Strings.unknown_error_message),
                onRefresh = onRefresh,
            )
        }

        DataLoadingState.NetworkError -> {
            ErrorContent(
                modifier = modifier,
                errorMessage = stringResource(Strings.network_error_message),
                onRefresh = onRefresh,
            )
        }

        DataLoadingState.Success -> {
            content()
        }
    }
}

@Composable
private fun DefaultLoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRefresh: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement
            .spacedBy(
                alignment = Alignment.CenterVertically,
                space = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge
        )
        PrimaryButton(
            text = stringResource(Strings.refresh),
            onClick = onRefresh
        )
    }
}
