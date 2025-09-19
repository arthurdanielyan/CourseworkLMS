package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlin.reflect.KClass

interface AppRouter {

    fun <T : Destination> navigate(
        destination: T,
        popUpTo: KClass<*>? = null,
        inclusive: Boolean = false,
    )

    fun pop()
}