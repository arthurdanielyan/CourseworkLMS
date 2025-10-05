package com.coursework.corePresentation.navigation.nestedNavigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.coursework.corePresentation.Destination
import com.coursework.corePresentation.extensions.ComposeCollect
import com.coursework.corePresentation.navigation.NavEvent
import com.coursework.corePresentation.navigation.NavEventHolder
import org.koin.compose.koinInject

@Composable
fun ListenNavEvents() {
    val navEventHolder = koinInject<NavEventHolder>()
    val nestedNavControllers = LocalNestedNavControllersProvider.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    LaunchedEffect(Unit) {

        nestedNavControllers.currentNavController.graph.forEach { destination ->

            Log.d("yapping", destination.route.toString())
            Log.d("yapping", (destination is NavGraph).toString())
        }
    }
    ComposeCollect(
        flow = navEventHolder.navEvents,
    ) { navEvent ->
        when (navEvent) {
            is NavEvent.Navigate<*> -> {
                val navController = nestedNavControllers.currentNavController
                    .takeIf {
                        it.graph.containsRecursive(navEvent.destination)
                    } // the root will handle it (hopefully)


                navController?.navigate(navEvent.destination) {
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
                val navController = nestedNavControllers.currentNavController
                    .takeIf { it.previousBackStackEntry != null }
                    ?: nestedNavControllers.parentNavController
                navController?.popBackstackIfResumed(lifecycle)
            }
        }
    }
}

private fun NavController.popBackstackIfResumed(lifecycle: Lifecycle) {
    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}

fun <T : Destination> NavGraph.containsRecursive(targetDestination: T): Boolean {
    forEach { destination ->

        if (destination.route?.startsWith(targetDestination::class.qualifiedName.orEmpty()) == true) return true
        if (destination is NavGraph && destination.containsRecursive(targetDestination)) return true
    }
    return false
}