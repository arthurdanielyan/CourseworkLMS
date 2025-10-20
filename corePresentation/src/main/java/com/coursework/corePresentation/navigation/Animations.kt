package com.coursework.corePresentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

val SlideInFromRight = slideInHorizontally { it }
val SlideOutToLeft = slideOutHorizontally { -it }
val SlideInFromLeft = slideInHorizontally { -it }
val SlideOutToRight = slideOutHorizontally { it }
