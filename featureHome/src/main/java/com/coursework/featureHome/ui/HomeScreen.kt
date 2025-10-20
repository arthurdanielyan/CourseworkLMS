package com.coursework.featureHome.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coursework.corePresentation.Destination
import com.coursework.corePresentation.extensions.sharedViewModel
import com.coursework.corePresentation.navigation.SlideInFromLeft
import com.coursework.corePresentation.navigation.SlideInFromRight
import com.coursework.corePresentation.navigation.SlideOutToLeft
import com.coursework.corePresentation.navigation.SlideOutToRight
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureHome.presentation.HomeUiCallbacks
import com.coursework.featureHome.presentation.HomeViewModel
import com.coursework.featureHome.ui.bottomBar.BottomBar
import com.coursework.featureSearchBooks.booksList.BooksListDestination
import com.coursework.featureSearchBooks.booksList.ui.SearchBooksScreen
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersDestination
import com.coursework.featureSearchBooks.searchFilters.ui.SearchFiltersScreen
import com.coursework.featureSearchBooks.shared.SearchBooksDestination
import com.coursework.featureSearchBooks.shared.SearchBooksSharedViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
object DummyScreenDestination : Destination

@Composable
fun HomeScreen() {

    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val callbacks: HomeUiCallbacks = viewModel

    val navController = registerNavController(HomeNavigationKey)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.weight(1f),
            navController = navController,
            startDestination = SearchBooksDestination,
            enterTransition = { SlideInFromRight },
            popEnterTransition = { SlideInFromLeft },
            exitTransition = { SlideOutToLeft },
            popExitTransition = { SlideOutToRight },
        ) {
            navigation<SearchBooksDestination>(
                startDestination = BooksListDestination,
            ) {
                composable<BooksListDestination> {
                    val searchBooksSharedViewModel =
                        it.sharedViewModel<SearchBooksSharedViewModel>(navController)
                    SearchBooksScreen(
                        sharedViewModel = searchBooksSharedViewModel
                    )
                }

                composable<SearchFiltersDestination> {
                    val searchBooksSharedViewModel =
                        it.sharedViewModel<SearchBooksSharedViewModel>(navController)
                    SearchFiltersScreen(
                        sharedViewModel = searchBooksSharedViewModel
                    )
                }
            }

            composable<DummyScreenDestination> {
                BackHandler {
                    callbacks.onBackClick()
                }
                DummyScreen()
            }
        }
        BottomBar(
            bottomBarType = state.bottomBarType,
            selectedItem = state.selectedItem,
            onTabSelected = callbacks::onBottomTabSelected
        )
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

const val HomeNavigationKey = "home_navigation"