package com.omarkarimli.morty.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.rounded.Add
import androidx.compose.ui.graphics.vector.ImageVector
import com.omarkarimli.morty.core.constants.Constants

sealed class NavDestination(val title: String, val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    object Home : NavDestination(
        title = Constants.NAV_DESTINATION_HOME,
        route = "home_screen",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    object Episodes : NavDestination(
        title = Constants.NAV_DESTINATION_EPISODES,
        route = "episodes",
        selectedIcon = Icons.Filled.PlayArrow,
        unselectedIcon = Icons.Outlined.PlayArrow
    )
    object Dynamic : NavDestination(
        title = Constants.NAV_DESTINATION_DYNAMIC,
        route = "dynamic_download",
        selectedIcon = Icons.Rounded.Add,
        unselectedIcon = Icons.Outlined.Add
    )
}
