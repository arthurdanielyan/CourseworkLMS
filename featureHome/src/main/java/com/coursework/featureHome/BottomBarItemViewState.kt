package com.coursework.featureHome

import androidx.compose.runtime.Immutable
import com.coursework.featureHome.R.string as Strings
import com.coursework.featureHome.R.drawable as Drawables

@Immutable
sealed interface BottomBarType {

    val items: List<BottomBarItemViewState>

    data object Student : BottomBarType {
        override val items = listOf(
            BottomBarItemViewState.Search,
            BottomBarItemViewState.Favourites,
        )
    }

    data object Teacher : BottomBarType {
        override val items = listOf(
            BottomBarItemViewState.Search,
            BottomBarItemViewState.MyBooks,
        )
    }
}

enum class BottomBarItemViewState(
    val titleKey: Int,
    val icon: Int,
) {

    Search(
        titleKey = Strings.search,
        icon = Drawables.ic_search,
    ),

    Favourites(
        titleKey = Strings.favourites,
        icon = Drawables.ic_favourites,
    ),

    MyBooks(
        titleKey = Strings.my_books,
        icon = Drawables.ic_books,
    ),
}