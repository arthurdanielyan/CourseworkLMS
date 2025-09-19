package com.coursework.lms

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursework.corePresentation.extensions.ComposeCollect
import com.coursework.corePresentation.navigation.NavEvent
import com.coursework.corePresentation.navigation.NavEventHolder
import com.coursework.featurelogin.LoginDestination
import com.coursework.featurelogin.ui.LoginScreen
import org.koin.compose.koinInject

@Composable
internal fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LoginDestination
    ) {
        composable<LoginDestination> {
            LoginScreen()
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
                navController.navigate(navEvent.destination)
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
