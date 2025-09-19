package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlin.reflect.KClass

sealed interface NavEvent {

    data class Navigate<T : Destination>(
        val destination: T,
        val popUpTo: KClass<*>?,
        val inclusive: Boolean
    ) : NavEvent

    object Pop : NavEvent
}