package com.omarkarimli.morty.core.commonui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.omarkarimli.morty.core.navigation.NavDestination

@Composable
fun MyBottomBar(
    currentRoute: String,
    onNavigationClick: (route: String) -> Unit
) {
    val navigationItems = listOf(
        NavDestination.Home,
        NavDestination.Dynamic,
        NavDestination.Episodes
    )

    NavigationBar {
        navigationItems.forEach { destination ->
            val isActive = when (destination.route) {
                NavDestination.Home.route -> currentRoute == destination.route ||
                        currentRoute.contains("character_details") ||
                        currentRoute.contains("character_episodes")
                NavDestination.Episodes.route -> currentRoute == destination.route
                NavDestination.Dynamic.route -> currentRoute == destination.route
                else -> currentRoute == destination.route
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        destination.icon,
                        contentDescription = destination.title,
                    )
                },
                label = { Text(destination.title) },
                selected = isActive,
                onClick = { onNavigationClick(destination.route) },
            )
        }
    }
}