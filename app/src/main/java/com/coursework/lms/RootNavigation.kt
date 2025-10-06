package com.coursework.lms

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coursework.corePresentation.navigation.destinations.BookDetailsDestination
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.navigation.destinations.HomeScreenDestination
import com.coursework.corePresentation.navigation.destinations.LoginDestination
import com.coursework.corePresentation.navigation.registerNavController
import com.coursework.featureBookDetails.ui.BookDetailsScreen
import com.coursework.featureEditBook.ui.EditBookScreen
import com.coursework.featureHome.ui.HomeScreen
import com.coursework.featurelogin.ui.LoginScreen
import kotlin.reflect.KClass

private val appScreens = mapOf<KClass<*>, @Composable (NavBackStackEntry) -> Unit>(
    LoginDestination::class to { LoginScreen() },
    HomeScreenDestination::class to { HomeScreen() },
    BookDetailsDestination::class to { BookDetailsScreen(it.toRoute()) },
    EditBookDestination::class to { EditBookScreen(it.toRoute()) },
)

@Composable
internal fun RootNavigation() {
    val navController = registerNavController(RootNavigationKey)
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
        appScreens.forEach { destinationClass, screenContent ->
            composable(destinationClass) {
                screenContent(it)
            }
        }
    }
}

const val RootNavigationKey = "root_navigation"
