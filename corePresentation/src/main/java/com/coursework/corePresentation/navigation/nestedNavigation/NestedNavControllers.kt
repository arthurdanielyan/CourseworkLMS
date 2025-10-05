package com.coursework.corePresentation.navigation.nestedNavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

class NestedNavControllers(
    val parentNavController: NavController?,
    val currentNavController: NavController,
)

val LocalNestedNavControllersProvider = compositionLocalOf<NestedNavControllers> {
    error("No NestedNavControllersProvider found")
}

@Composable
fun ProvideCurrentNavController(
    navController: NavController,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNestedNavControllersProvider provides NestedNavControllers(
            parentNavController = LocalNestedNavControllersProvider.current.currentNavController,
            currentNavController = navController
        ),
        content = content
    )
}
