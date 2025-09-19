package com.coursework.corePresentation.navigation

import com.coursework.corePresentation.Destination

interface AppRouter {

    fun <T : Destination> navigate(destination: T)

    fun pop()
}