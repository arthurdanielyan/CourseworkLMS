package com.coursework.corePresentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun OnLifecycleEvents(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: LifecycleEventHandler.() -> Unit
) {
    val handler = remember { LifecycleEventHandler().apply(block) }
    val lifecycle = lifecycleOwner.lifecycle

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            handler.handle(event)
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

class LifecycleEventHandler {
    private var onCreate: (() -> Unit)? = null
    private var onStart: (() -> Unit)? = null
    private var onResume: (() -> Unit)? = null
    private var onPause: (() -> Unit)? = null
    private var onStop: (() -> Unit)? = null
    private var onDestroy: (() -> Unit)? = null

    fun onCreate(block: () -> Unit) {
        onCreate = block
    }

    fun onStart(block: () -> Unit) {
        onStart = block
    }

    fun onResume(block: () -> Unit) {
        onResume = block
    }

    fun onPause(block: () -> Unit) {
        onPause = block
    }

    fun onStop(block: () -> Unit) {
        onStop = block
    }

    fun onDestroy(block: () -> Unit) {
        onDestroy = block
    }

    internal fun handle(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onCreate?.invoke()
            Lifecycle.Event.ON_START -> onStart?.invoke()
            Lifecycle.Event.ON_RESUME -> onResume?.invoke()
            Lifecycle.Event.ON_PAUSE -> onPause?.invoke()
            Lifecycle.Event.ON_STOP -> onStop?.invoke()
            Lifecycle.Event.ON_DESTROY -> onDestroy?.invoke()
            else -> Unit
        }
    }
}
