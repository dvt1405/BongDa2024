package com.kt.apps.media.football.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import com.kt.apps.media.football.R

data class BottomBarNavItem(
    val selected: Boolean,
    val icon: ImageVector,
    @StringRes
    val label: Int,
    val onClick: () -> Unit,
)

internal val defaultBottomBarItems = arrayOf(
    BottomBarNavItem(
        selected = false,
        icon = Icons.Rounded.Home,
        label = R.string.home_title,
        onClick = {}
    ),
    BottomBarNavItem(
        selected = false,
        icon = Icons.Rounded.Favorite,
        label = R.string.highlight_title,
        onClick = {}
    ),
    BottomBarNavItem(
        selected = false,
        icon = Icons.Rounded.Settings,
        label = R.string.settings_title,
        onClick = {}
    ),
)

@Composable
fun HomeNavigationBar(
    bottomBarVisibleState: State<Boolean>,
    bottomBarSelectedState: State<Int>,
    onItemClick: (Int) -> Unit
) {
    AnimatedVisibility(
        visible = bottomBarVisibleState.value,
        enter = slideInVertically(
            initialOffsetY = { it },
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
        )
    ) {
        BottomAppBar {
            NavigationBar {
                defaultBottomBarItems.forEachIndexed { index, bottomBarNavItem ->
                    NavigationBarItem(
                        selected = bottomBarSelectedState.value == index,
                        onClick = {
                            onItemClick.invoke(index)
                        },
                        icon = {
                            Icon(
                                painter = rememberVectorPainter(image = bottomBarNavItem.icon),
                                contentDescription = stringResource(id = bottomBarNavItem.label)
                            )
                        },
                        label = {
                            Text(text = stringResource(id = bottomBarNavItem.label))
                        }
                    )
                }
            }
        }
    }
}