package com.coursework.lms

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coursework.corePresentation.navigation.SlideInFromLeft
import com.coursework.corePresentation.navigation.SlideInFromRight
import com.coursework.corePresentation.navigation.SlideOutToLeft
import com.coursework.corePresentation.navigation.SlideOutToRight
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
    BookDetailsDestination::class to { BookDetailsScreen(it.toRoute()) },
    EditBookDestination::class to { EditBookScreen(it.toRoute()) },
)

@Composable
internal fun RootNavigation() {
    val navController = registerNavController(RootNavigationKey)
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        enterTransition = { SlideInFromRight },
        exitTransition = { SlideOutToLeft },
        popEnterTransition = { SlideInFromLeft },
        popExitTransition = { SlideOutToRight }
    ) {
        appScreens.forEach { destinationClass, screenContent ->
            composable(destinationClass) {
                screenContent(it)
            }
        }

        composable<LoginDestination>(
            enterTransition = { slideInHorizontally { -it } },
        ) {
            LoginScreen()
        }

        composable<HomeScreenDestination>(
            exitTransition = {
                getHomeScreenExitTransition(targetState)
            }
        ) {
            HomeScreen()
        }
    }
}

fun getHomeScreenExitTransition(
    target: NavBackStackEntry
): ExitTransition {

    return when {
        target.destination.route.orEmpty()
            .startsWith(LoginDestination::class.qualifiedName.orEmpty()) -> {
            SlideOutToRight
        }

        else -> SlideOutToLeft
    }
}

const val RootNavigationKey = "root_navigation"
