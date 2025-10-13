package com.coursework.lms

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.coursework.corePresentation.Destination
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.NavControllersHolder
import com.coursework.corePresentation.navigation.destinations.BookDetailsDestination
import com.coursework.corePresentation.navigation.destinations.EditBookDestination
import com.coursework.corePresentation.navigation.destinations.HomeScreenDestination
import com.coursework.corePresentation.navigation.destinations.LoginDestination
import com.coursework.featureHome.ui.DummyScreenDestination
import com.coursework.featureHome.ui.HomeNavigationKey
import com.coursework.featureSearchBooks.searchFilters.SearchFiltersDestination
import com.coursework.featureSearchBooks.shared.SearchBooksDestination
import kotlin.reflect.KClass

class AppRouterImplV2 : AppRouter, NavControllersHolder {

    private val controllers = linkedMapOf<String, NavHostController>()

    override fun <T : Destination> navigate(
        destination: T,
        popUpTo: KClass<*>?,
        popUpToStart: Boolean,
        inclusive: Boolean,
        saveState: Boolean
    ) {
        val controller = when (destination) {
            LoginDestination,
            HomeScreenDestination,
            is BookDetailsDestination,
            is EditBookDestination -> controllers[RootNavigationKey]

            SearchBooksDestination,
            SearchFiltersDestination,
            DummyScreenDestination -> controllers[HomeNavigationKey]

            else -> null
        }

        controller?.navigate(destination) {
            when {
                popUpToStart -> popUpTo(controller.graph.findStartDestination().id) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }

                popUpTo != null -> popUpTo(popUpTo) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun pop() {
        val orderedControllers = controllers.values.toList().asReversed()
        for (controller in orderedControllers) {
            if (controller.previousBackStackEntry != null) {
                controller.popBackstackIfResumed(controller.currentBackStackEntry?.lifecycle)
                return
            }
        }
    }

    override fun register(key: String, controller: NavHostController) {
        controllers[key] = controller
    }

    override fun unregister(key: String) {
        controllers.remove(key)
    }

    private fun NavController.popBackstackIfResumed(lifecycle: Lifecycle?) {
        if (lifecycle?.currentState == Lifecycle.State.RESUMED) {
            popBackStack()
        }
    }
}