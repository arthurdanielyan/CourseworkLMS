package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.reflect.KClass

internal class AppRouterImpl : AppRouter, NavEventHolder {

    private val _navEvents = Channel<NavEvent>()
    override val navEvents = _navEvents.receiveAsFlow()

    override fun <T : Destination> navigate(
        destination: T,
        popUpTo: KClass<*>?,
        inclusive: Boolean,
        saveState: Boolean,
    ) {
        _navEvents.trySend(
            NavEvent.Navigate(
                destination = destination,
                popUpTo = popUpTo,
                inclusive = inclusive,
                saveState = saveState,
            )
        )
    }

    override fun pop() {
        _navEvents.trySend(NavEvent.Pop)
    }
}