package com.coursework.featureHome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.domain.model.UserType
import com.coursework.domain.usecases.GetUserTypeUseCase
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.BottomBarType
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserTypeUseCase: GetUserTypeUseCase,
) : ViewModel(), HomeUiCallbacks {

    private val bottomBarType = MutableStateFlow<BottomBarType>(BottomBarType.Student)
    private val selectedItem = MutableStateFlow(BottomBarItemViewState.Search)
    val uiState = combine(
        bottomBarType,
        selectedItem,
    ) { bottomBarType, selectedItem ->

        HomeViewState(
            bottomBarType = bottomBarType,
            selectedItem = selectedItem,
        )
    }.stateInWhileSubscribed(viewModelScope, HomeViewState())

    init {
        getUserType()
    }

    private fun getUserType() {
        viewModelScope.launch {
            when(getUserTypeUseCase()) {
                UserType.Student -> {
                    bottomBarType.update {
                        BottomBarType.Student
                    }
                }
                UserType.Teacher -> {
                    bottomBarType.update {
                        BottomBarType.Teacher
                    }
                }
            }
        }
    }

    override fun onBottomTabSelected(item: BottomBarItemViewState) {
        selectedItem.update {
            item
        }
    }
}