package com.coursework.featurelogin.presentation

import androidx.compose.runtime.Immutable

@Immutable
interface LoginUiCallbacks {

    fun onEmailType(value: String)
    fun onPasswordType(value: String)
    fun onLoginAsStudentClick()
    fun onLoginAsTeacherClick()
}