package com.coursework.corePresentation.navigation

import kotlinx.coroutines.flow.Flow

interface NavEventHolder {

    val navEvents: Flow<NavEvent>
}