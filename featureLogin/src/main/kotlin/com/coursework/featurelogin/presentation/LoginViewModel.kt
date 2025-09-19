package com.coursework.featurelogin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.featureSearchBooks.SearchBooksDestination
import com.coursework.featurelogin.LoginDestination
import com.coursework.utils.stateInWhileSubscribed
import com.coursework.utils.stringProvider.StringProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import com.coursework.featurelogin.R.string as Strings

class LoginViewModel(
    private val appRouter: AppRouter,
    private val stringProvider: StringProvider,
) : ViewModel(), LoginUiCallbacks {

    private val emailInput = MutableStateFlow("")
    private val passwordInput = MutableStateFlow("")

    val uiState = combine(
        emailInput,
        passwordInput,
    ) { emailInput, passwordInput ->

        val emailMessageError = stringProvider.string(Strings.invalid_email_message)
            .takeIf { emailInput.isNotBlank() && isEmailValid(emailInput).not() }

        LoginViewState(
            emailInput = emailInput,
            passwordInput = passwordInput,
            emailErrorMessage = emailMessageError,
        )
    }.stateInWhileSubscribed(viewModelScope, LoginViewState())

    private fun isEmailValid(value: String): Boolean {
        return value.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

    override fun onEmailType(value: String) {
        emailInput.update { value }
    }

    override fun onPasswordType(value: String) {
        passwordInput.update { value }
    }

    override fun onLoginAsStudentClick() {
        // TODO: Not yet implemented
        appRouter.navigate(
            destination = SearchBooksDestination,
            popUpTo = LoginDestination::class,
            inclusive = true,
        )
    }

    override fun onLoginAsTeacherClick() {
        // TODO: Not yet implemented
    }
}