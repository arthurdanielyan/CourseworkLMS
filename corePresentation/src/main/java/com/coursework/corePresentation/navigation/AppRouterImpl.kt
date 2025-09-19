package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AppRouterImpl : AppRouter, NavEventHolder {

    private val _navEvents = Channel<NavEvent>()
    override val navEvents = _navEvents.receiveAsFlow()

    override fun <T : Destination> navigate(destination: T) {
        _navEvents.trySend(
            NavEvent.Navigate(destination)
        )
    }

    override fun pop() {
        _navEvents.trySend(NavEvent.Pop)
    }
}