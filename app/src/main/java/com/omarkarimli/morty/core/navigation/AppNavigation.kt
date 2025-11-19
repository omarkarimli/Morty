package com.omarkarimli.morty.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.MyBottomBar
import com.omarkarimli.morty.features.allepisodes.ui.AllEpisodesScreen
import com.omarkarimli.morty.features.characterdetails.ui.CharacterDetailsScreen
import com.omarkarimli.morty.features.episode.ui.CharacterEpisodeScreen
import com.omarkarimli.morty.features.allcharacters.ui.HomeScreen
import com.omarkarimli.morty.core.commonui.MyTopBar
import com.omarkarimli.network.KtorClient

data class NavigationState(
    val currentRoute: String,
    val title: String,
    val showBackButton: Boolean = false
)

@Composable
private fun getNavigationState(
    currentRoute: String
): NavigationState {
    return when {
        currentRoute == NavDestination.Home.route -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_home)
        )
        currentRoute == NavDestination.Episodes.route -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_episodes)
        )
        currentRoute.contains("character_details") -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_character_details),
            showBackButton = true
        )
        currentRoute.contains("character_episodes") -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.nav_title_character_episodes),
            showBackButton = true
        )
        else -> NavigationState(
            currentRoute = currentRoute,
            title = stringResource(R.string.app_title_default)
        )
    }
}

@Composable
fun AppNavigation(ktorClient: KtorClient) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: NavDestination.Home.route
    
    // Create navigation state with title mapping
    val navigationState = getNavigationState(currentRoute)

    Scaffold(
        topBar = {
            MyTopBar(
                title = navigationState.title,
                showBackButton = navigationState.showBackButton,
                onBackClick = if (navigationState.showBackButton) {
                    { navController.navigateUp() }
                } else null,
            )
        },
        bottomBar = {
            MyBottomBar(
                currentRoute = currentRoute,
                onNavigationClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavigationHost(
            navController = navController,
            ktorClient = ktorClient,
            innerPadding = innerPadding
        )
    }
}



@Composable
fun AppNavigationHost(
    navController: NavHostController,
    ktorClient: KtorClient,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Home.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = NavDestination.Home.route) {
            HomeScreen(
                onCharacterSelected = { characterId ->
                    navController.navigate("character_details/$characterId")
                }
            )
        }

        composable(
            route = "character_details/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterDetailsScreen(
                characterId = characterId,
                onEpisodeClicked = { episodeId -> navController.navigate("character_episodes/$episodeId") },
                onBackClicked = { navController.navigateUp() }
            )
        }

        composable(
            route = "character_episodes/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
            CharacterEpisodeScreen(
                characterId = characterId,
                ktorClient = ktorClient
            )
        }

        composable(route = NavDestination.Episodes.route) {
            AllEpisodesScreen()
        }
    }
}