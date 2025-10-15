package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlin.reflect.KClass

interface AppRouter {

    /**
     * (When I wrote this code only I and God knew how it works. Now only God knows)
     *
     * @param popAll When true all destinations of the topmost navigation graph
     * are removed without saving states. When true [popUpToStart] is ignored
     * therefore [popUpTo] and [inclusive] are also ignored
     *
     * @param popUpToStart When true pops up to the start destination of
     * the topmost navigation graph
     *
     * [popUpTo] and [inclusive] should be used with [popUpToStart] or [popAll]
     *
     * */
    fun <T : Destination> navigate(
        destination: T,
        popAll: Boolean = false,
        popUpTo: KClass<*>? = null,
        popUpToStart: Boolean = false,
        inclusive: Boolean = false,
        saveState: Boolean = false
    )

    fun pop()
}