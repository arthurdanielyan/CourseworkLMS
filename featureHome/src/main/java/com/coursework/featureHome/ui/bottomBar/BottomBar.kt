package com.coursework.featureHome.ui.bottomBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.BottomBarType
import com.coursework.featureHome.ui.bottomBar.BottomBarItem

@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    bottomBarType: BottomBarType,
    selectedItem: BottomBarItemViewState,
    onTabSelected: (BottomBarItemViewState) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        bottomBarType.items.forEach { item ->
            BottomBarItem(
                modifier = Modifier.weight(1f),
                item = item,
                isSelected = selectedItem == item,
                onClick = {
                    onTabSelected(item)
                }
            )
        }
    }
}


