package com.coursework.featureHome.ui.bottomBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.coursework.featureHome.BottomBarItemViewState

@Composable
internal fun RowScope.BottomBarItem(
    modifier: Modifier = Modifier,
    item: BottomBarItemViewState,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        modifier = modifier,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.inversePrimary,
            selectedTextColor = MaterialTheme.colorScheme.inverseSurface,
            indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
            unselectedTextColor = MaterialTheme.colorScheme.onBackground
        ),
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                modifier = Modifier
                    .size(45.dp),
                painter = painterResource(item.icon),
                contentDescription = stringResource(item.titleKey)
            )
        },
        label = {
            Text(
                text = stringResource(item.titleKey),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    )
}

internal val BottomBarHeight = 80.dp
