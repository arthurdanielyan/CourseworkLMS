package com.coursework.featureHome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursework.corePresentation.Destination
import com.coursework.corePresentation.navigation.AppRouter
import com.coursework.domain.model.UserType
import com.coursework.domain.usecases.GetUserTypeUseCase
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.BottomBarType
import com.coursework.featureHome.ui.DummyScreenDestination
import com.coursework.featureSearchBooks.shared.SearchBooksDestination
import com.coursework.utils.stateInWhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val appRouter: AppRouter
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

    private fun BottomBarItemViewState.toDestination(): Destination {
        return when (this) {
            BottomBarItemViewState.Search -> SearchBooksDestination
            BottomBarItemViewState.Favourites -> DummyScreenDestination
            BottomBarItemViewState.MyBooks -> DummyScreenDestination
        }
    }

    override fun onBottomTabSelected(item: BottomBarItemViewState) {
        appRouter.navigate(
            destination = item.toDestination(),
            popUpToStart = true,
            saveState = true
        )
        selectedItem.update {
            item
        }
    }

    override fun onBackClick() {
        appRouter.navigate(
            destination = SearchBooksDestination,
            popUpToStart = true,
            saveState = true
        )
        selectedItem.update {
            BottomBarItemViewState.Search
        }
    }
}