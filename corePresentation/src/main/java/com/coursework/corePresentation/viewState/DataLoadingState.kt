package com.coursework.corePresentation.viewState

import java.io.IOException

enum class DataLoadingState {
    Loading, Success, Error, NetworkError
}

fun Throwable.toDataLoadingState(): DataLoadingState {
    return when (this) {
        is IOException -> DataLoadingState.NetworkError // TODO: Implement custom network exception
        else -> DataLoadingState.Error
    }
}
