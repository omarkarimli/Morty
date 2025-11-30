package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.core.navigation.NavDestination
import com.omarkarimli.morty.ui.theme.Dimens

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

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            navigationItems.forEach { destination ->
                val isActive = when (destination.route) {
                    NavDestination.Home.route -> currentRoute == destination.route ||
                            currentRoute.contains("character_details") ||
                            currentRoute.contains("character_episodes")
                    NavDestination.Episodes.route -> currentRoute == destination.route
                    NavDestination.Dynamic.route -> currentRoute == destination.route
                    else -> currentRoute == destination.route
                }

                val containerColor = if (destination == NavDestination.Dynamic) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    if (isActive) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                }

                val contentColor = if (destination == NavDestination.Dynamic) {
                    MaterialTheme.colorScheme.surface
                } else {
                    if (isActive) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                }

                FilledTonalIconButton(
                    modifier = Modifier
                        .padding(
                            start = Dimens.dp8,
                            end = Dimens.dp8,
                            top = Dimens.dp8,
                            bottom = Dimens.dp24
                        )
                        .width(Dimens.dp48)
                        .height(Dimens.dp32),
                    onClick = { onNavigationClick(destination.route) },
                    shape = IconButtonDefaults.filledShape,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = containerColor,
                        contentColor = contentColor
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(Dimens.dp24),
                        imageVector = if (isActive) destination.selectedIcon else destination.unselectedIcon,
                        contentDescription = stringResource(destination.titleResId),
                    )
                }
            }
        }
    }
}
