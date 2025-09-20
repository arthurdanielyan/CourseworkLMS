package com.coursework.featureHome.presentation

import androidx.compose.runtime.Immutable
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.BottomBarType

@Immutable
data class HomeViewState(
    val bottomBarType: BottomBarType = BottomBarType.Student,
    val selectedItem: BottomBarItemViewState = BottomBarItemViewState.Search,
)
