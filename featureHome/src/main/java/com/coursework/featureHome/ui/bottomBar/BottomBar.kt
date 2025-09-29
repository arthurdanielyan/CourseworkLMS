package com.coursework.featureHome.ui.bottomBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coursework.featureHome.BottomBarItemViewState
import com.coursework.featureHome.BottomBarType

@Composable
internal fun BottomBar(
    modifier: Modifier = Modifier,
    bottomBarType: BottomBarType,
    selectedItem: BottomBarItemViewState,
    onTabSelected: (BottomBarItemViewState) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.inversePrimary)
            .navigationBarsPadding(),
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


