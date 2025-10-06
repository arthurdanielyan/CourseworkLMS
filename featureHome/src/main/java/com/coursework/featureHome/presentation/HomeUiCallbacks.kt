package com.coursework.featureHome.presentation

import androidx.compose.runtime.Immutable
import com.coursework.featureHome.BottomBarItemViewState

@Immutable
interface HomeUiCallbacks {

    fun onBottomTabSelected(item: BottomBarItemViewState)
    fun onBackClick()
}