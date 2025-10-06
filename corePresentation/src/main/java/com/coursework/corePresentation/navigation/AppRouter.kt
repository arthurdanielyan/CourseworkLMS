package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlin.reflect.KClass

interface AppRouter {

    /**
     * When [popUpToStart] is true [popUpTo] is ignored
     * */
    fun <T : Destination> navigate(
        destination: T,
        popUpTo: KClass<*>? = null,
        popUpToStart: Boolean = false,
        inclusive: Boolean = false,
        saveState: Boolean = false
    )

    fun pop()
}