package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination

sealed interface NavEvent {

    data class Navigate<T : Destination>(
        val destination: T
    ) : NavEvent

    object Pop : NavEvent
}