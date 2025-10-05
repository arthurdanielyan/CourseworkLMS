package com.coursework.featureHome.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.coursework.corePresentation.Destination
import com.coursework.corePresentation.extensions.ObserveState
import com.coursework.corePresentation.navigation.nestedNavigation.ListenNavEvents
import com.coursework.corePresentation.navigation.nestedNavigation.ProvideCurrentNavController
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.presentation.HomeUiCallbacks
import com.coursework.featureHome.presentation.HomeViewModel
import com.coursework.featureHome.ui.bottomBar.BottomBar
import com.coursework.featureSearchBooks.BookSearchFiltersDestination
import com.coursework.featureSearchBooks.BooksListDestination
import com.coursework.featureSearchBooks.SearchBooksDestination
import com.coursework.featureSearchBooks.ui.SearchBooksScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object DummyScreenDestination : Destination

@Composable
fun HomeScreen() {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: HomeUiCallbacks = viewModel

    val navController = rememberNavController()

    ObserveState(state.selectedItem) { selectedBottomTab ->
        navController.navigate(selectedBottomTab.toDestination()) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    ProvideCurrentNavController(navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = SearchBooksDestination,
            ) {
                navigation<SearchBooksDestination>(
                    startDestination = BooksListDestination
                ) {
                    composable<BooksListDestination> {
                        SearchBooksScreen()
                    }

                    composable<BookSearchFiltersDestination> {
                        Box(Modifier.fillMaxSize()) {
                            Text(text = "Book search filters")
                        }
                    }
                }

                composable<DummyScreenDestination> {
                    DummyScreen()
                }
            }
            ListenNavEvents()
            BottomBar(
                bottomBarType = state.bottomBarType,
                selectedItem = state.selectedItem,
                onTabSelected = callbacks::onBottomTabSelected
            )
        }
    }
}

@Composable
private fun DummyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Shaurma aranc sox")
    }
}

private fun BottomBarItemViewState.toDestination(): Destination {
    return when (this) {
        BottomBarItemViewState.Search -> SearchBooksDestination
        BottomBarItemViewState.Favourites -> DummyScreenDestination
        BottomBarItemViewState.MyBooks -> DummyScreenDestination
    }
}
