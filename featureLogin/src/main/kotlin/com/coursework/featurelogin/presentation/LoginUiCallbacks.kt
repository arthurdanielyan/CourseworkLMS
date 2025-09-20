package com.coursework.featurelogin.presentation

import androidx.compose.runtime.Immutable

@Immutable
internal interface LoginUiCallbacks {

    fun onEmailType(value: String)
    fun onPasswordType(value: String)
    fun onLoginClick()
    fun onLoginAsStudentClick()
    fun onLoginAsTeacherClick()
}