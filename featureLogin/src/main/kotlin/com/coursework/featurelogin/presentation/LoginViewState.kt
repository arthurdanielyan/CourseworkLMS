package com.coursework.featurelogin.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal data class LoginViewState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val emailErrorMessage: String? = null,
) {

    val isEmailInputError = emailErrorMessage?.isNotBlank() == true
}