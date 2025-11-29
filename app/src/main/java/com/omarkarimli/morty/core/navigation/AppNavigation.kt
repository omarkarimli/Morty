package com.omarkarimli.morty.core.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.omarkarimli.morty.core.commonui.MyTopBar
import com.omarkarimli.morty.features.allcharacters.ui.HomeScreen
import com.omarkarimli.morty.features.allepisodes.ui.AllEpisodesScreen
import com.omarkarimli.morty.features.characterdetails.ui.CharacterDetailsScreen
import com.omarkarimli.morty.features.episode.ui.CharacterEpisodeScreen
import com.omarkarimli.network.KtorClient
import kotlinx.coroutines.launch

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

private const val MAIN_SCREEN_ROUTE = "main_screen?index={index}"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(ktorClient: KtorClient) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MAIN_SCREEN_ROUTE
    
    // Pager state for top-level tabs
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    // Determine effective route for UI logic (BottomBar, TopBar)
    // If we are on the main screen container, the effective route depends on the active pager tab.
    val effectiveRoute = if (currentRoute == MAIN_SCREEN_ROUTE) {
        when (pagerState.currentPage) {
            0 -> NavDestination.Home.route
            1 -> NavDestination.Episodes.route
            else -> NavDestination.Home.route
        }
    } else {
        currentRoute
    }

    // Create navigation state with title mapping
    val navigationState = getNavigationState(effectiveRoute)

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
                currentRoute = effectiveRoute,
                onNavigationClick = { route ->
                    // Handle navigation based on target route
                    when (route) {
                        NavDestination.Home.route -> {
                            if (currentRoute == MAIN_SCREEN_ROUTE) {
                                coroutineScope.launch { pagerState.animateScrollToPage(0) }
                            } else {
                                navController.navigate("main_screen?index=0") {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                        NavDestination.Episodes.route -> {
                            if (currentRoute == MAIN_SCREEN_ROUTE) {
                                coroutineScope.launch { pagerState.animateScrollToPage(1) }
                            } else {
                                navController.navigate("main_screen?index=1") {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            }
                        }
                        else -> {
                            navController.navigate(route)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavigationHost(
            navController = navController,
            ktorClient = ktorClient,
            innerPadding = innerPadding,
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigationHost(
    navController: NavHostController,
    ktorClient: KtorClient,
    innerPadding: PaddingValues,
    pagerState: androidx.compose.foundation.pager.PagerState
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_SCREEN_ROUTE,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(
            route = MAIN_SCREEN_ROUTE,
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            LaunchedEffect(index) {
                pagerState.scrollToPage(index)
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> HomeScreen(
                        onCharacterSelected = { characterId ->
                            navController.navigate("character_details/$characterId")
                        }
                    )
                    1 -> AllEpisodesScreen()
                }
            }
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
                onEpisodeClicked = { episodeId -> navController.navigate("character_episodes/$episodeId") }
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
    }
}