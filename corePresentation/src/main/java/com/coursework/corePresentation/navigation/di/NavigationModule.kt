package com.coursework.corePresentation.navigation.di

import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.corePresentation.navigation.AppRouterImpl
import com.coursework.corePresentation.navigation.NavEventHolder
import org.koin.dsl.module

val navigationModule = module {
    val appRouterImpl = AppRouterImpl()
    single<AppRouter> {
        appRouterImpl
    }
    single<NavEventHolder> {
        appRouterImpl
    }
}