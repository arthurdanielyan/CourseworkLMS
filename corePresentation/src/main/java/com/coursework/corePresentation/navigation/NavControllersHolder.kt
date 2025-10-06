package com.coursework.corePresentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

interface NavControllersHolder {

    fun register(key: String, controller: NavHostController)

    fun unregister(key: String)
}

val LocalNavControllersHolder = staticCompositionLocalOf<NavControllersHolder> {
    error("No AppRouter was provided")
}

@Composable
fun registerNavController(key: String): NavHostController {
    val navControllersHolder = LocalNavControllersHolder.current
    val navController = rememberNavController()
    DisposableEffect(Unit) {
        navControllersHolder.register(key, navController)
        onDispose {
            navControllersHolder.unregister(key)
        }
    }
    return navController
}
