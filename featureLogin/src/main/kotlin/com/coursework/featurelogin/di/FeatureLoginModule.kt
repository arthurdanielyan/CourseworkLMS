package com.coursework.featurelogin.di

import com.coursework.featurelogin.presentation.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureLoginModule = module {
    viewModelOf(::LoginViewModel)
}