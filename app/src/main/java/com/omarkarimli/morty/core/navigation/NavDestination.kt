package com.omarkarimli.morty.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector
import com.omarkarimli.morty.core.constants.Constants

sealed class NavDestination(val title: String, val route: String, val icon: ImageVector) {
    object Home : NavDestination(title = Constants.NAV_DESTINATION_HOME, route = "home_screen", icon = Icons.Filled.Home)
    object Episodes : NavDestination(title = Constants.NAV_DESTINATION_EPISODES, route = "episodes", icon = Icons.Filled.PlayArrow)
    object Dynamic : NavDestination(title = Constants.NAV_DESTINATION_DYNAMIC, route = "dynamic_download", icon = Icons.Filled.Download)
}