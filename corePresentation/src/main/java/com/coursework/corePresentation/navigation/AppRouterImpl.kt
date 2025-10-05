package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.reflect.KClass

internal class AppRouterImpl : AppRouter, NavEventHolder {

    private val _navEvents = MutableSharedFlow<NavEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val navEvents = _navEvents.asSharedFlow()

    override fun <T : Destination> navigate(
        destination: T,
        popUpTo: KClass<*>?,
        inclusive: Boolean,
        saveState: Boolean,
    ) {
        _navEvents.tryEmit(
            NavEvent.Navigate(
                destination = destination,
                popUpTo = popUpTo,
                inclusive = inclusive,
                saveState = saveState,
            )
        )
    }

    override fun pop() {
        _navEvents.tryEmit(NavEvent.Pop)
    }
}