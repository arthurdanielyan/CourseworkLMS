package com.coursework.lms

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coursework.corePresentation.extensions.ComposeCollect
import com.coursework.corePresentation.navigation.NavEvent
import com.coursework.corePresentation.navigation.NavEventHolder
import com.coursework.featureBookDetails.BookDetailsDestination
import com.coursework.featureBookDetails.ui.BookDetailsScreen
import com.coursework.featureHome.ui.HomeScreen
import com.coursework.featureHome.ui.HomeScreenDestination
import com.coursework.featurelogin.LoginDestination
import com.coursework.featurelogin.ui.LoginScreen
import org.koin.compose.koinInject

@Composable
internal fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        enterTransition = {
            slideInHorizontally { it }
        },
        exitTransition = {
            slideOutHorizontally { -it }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        }
    ) {
        composable<LoginDestination> {
            LoginScreen()
        }

        composable<HomeScreenDestination> {
            HomeScreen()
        }

        composable<BookDetailsDestination> {
            BookDetailsScreen(it.toRoute())
        }
    }
    ObserveNavEvents(navController)
}

@Composable
private fun ObserveNavEvents(
    navController: NavController
) {
    val navEventHolder = koinInject<NavEventHolder>()
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    ComposeCollect(
        flow = navEventHolder.navEvents,
    ) { navEvent ->
        when (navEvent) {
            is NavEvent.Navigate<*> -> {
                navController.navigate(navEvent.destination) {
                    val popUpToDestination = navEvent.popUpTo
                    if (popUpToDestination != null) {
                        popUpTo(popUpToDestination) {
                            inclusive = navEvent.inclusive
                            saveState = navEvent.saveState
                        }
                        restoreState = true
                    }
                    launchSingleTop = true
                }
            }

            NavEvent.Pop -> {
                navController.popBackstackIfResumed(lifecycle)
            }
        }
    }
}

private fun NavController.popBackstackIfResumed(lifecycle: Lifecycle) {
    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}
