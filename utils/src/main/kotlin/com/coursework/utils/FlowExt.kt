package com.coursework.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialState: T
) = this.stateIn(scope, SharingStarted.WhileSubscribed(DefaultTimeoutMillis), initialState)

private const val DefaultTimeoutMillis = 5000L

fun <T> MutableStateFlow<T>.updateIf(condition: Boolean, modifier: (T) -> T) {
    if (condition) {
        update(modifier)
    }
}