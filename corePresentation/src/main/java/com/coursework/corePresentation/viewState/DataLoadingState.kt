package com.coursework.corePresentation.viewState

import com.coursework.utils.stringProvider.StringProvider
import java.io.IOException
import com.coursework.corePresentation.R.string as Strings

enum class DataLoadingState {
    Loading, Success, Error, NetworkError
}

fun Throwable.toDataLoadingState(): DataLoadingState {
    return when (this) {
        is IOException -> DataLoadingState.NetworkError // TODO: Implement custom network exception
        else -> DataLoadingState.Error
    }
}

fun StringProvider.getErrorMessage(throwable: Throwable): String {
    return when (throwable) {
        is IOException -> string(Strings.network_error_message) // TODO: Implement custom network exception
        else -> string(Strings.unknown_error_message)
    }
}