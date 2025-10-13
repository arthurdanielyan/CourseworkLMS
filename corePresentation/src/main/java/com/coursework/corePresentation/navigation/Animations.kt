package com.coursework.corePresentation.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

val EnterTransition = slideInHorizontally { it }
val ExitTransition = slideOutHorizontally { -it }
val PopEnterTransition = slideInHorizontally { -it }
val PopExitTransition = slideOutHorizontally { it }

val FadeInTransition = fadeIn()
val FadeOutTransition = fadeOut()
