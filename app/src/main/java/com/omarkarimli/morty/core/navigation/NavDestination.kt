package com.omarkarimli.morty.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.graphics.vector.ImageVector
import com.omarkarimli.morty.R

sealed class NavDestination(
    @param:StringRes val titleResId: Int,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : NavDestination(
        titleResId = R.string.nav_title_home,
        route = "home_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    object Episodes : NavDestination(
        titleResId = R.string.nav_title_episodes,
        route = "episodes",
        selectedIcon = Icons.Filled.PlayArrow,
        unselectedIcon = Icons.Outlined.PlayArrow
    )
    object Dynamic : NavDestination(
        titleResId = R.string.nav_title_dynamic,
        route = "dynamic_download",
        selectedIcon = Icons.Rounded.Add,
        unselectedIcon = Icons.Outlined.Add
    )
}
