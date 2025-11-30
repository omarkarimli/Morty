package com.omarkarimli.morty.core.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.omarkarimli.morty.features.dynamic.DownloadScreen
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
        currentRoute == NavDestination.Dynamic.route -> NavigationState(
            currentRoute = currentRoute,
            title = "Dynamic Feature"
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
    val rootNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHostContainer(
            innerPadding = innerPadding,
            rootNavController = rootNavController,
            ktorClient = ktorClient
        )
    }
}

@Composable
fun NavHostContainer(
    innerPadding: PaddingValues,
    rootNavController: NavHostController,
    ktorClient: KtorClient
) {
    NavHost(
        navController = rootNavController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                ktorClient = ktorClient,
                onDynamicClick = {
                    rootNavController.navigate(NavDestination.Dynamic.route)
                }
            )
        }
        composable(NavDestination.Dynamic.route) {
            DownloadScreen(onBackClick = {
                rootNavController.navigateUp()
            })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(
    ktorClient: KtorClient,
    onDynamicClick: () -> Unit
) {
    val homeNavController = rememberNavController()
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    // Observe Home Nav Stack
    val homeBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val homeCurrentRoute = homeBackStackEntry?.destination?.route

    // Determine global current route/state
    val currentRoute = when (pagerState.currentPage) {
        0 -> homeCurrentRoute ?: NavDestination.Home.route
        1 -> NavDestination.Episodes.route
        else -> NavDestination.Home.route
    }

    // Create navigation state with title mapping
    val navigationState = getNavigationState(currentRoute)

    Scaffold(
        topBar = {
            MyTopBar(
                title = navigationState.title,
                showBackButton = navigationState.showBackButton,
                onBackClick = if (navigationState.showBackButton) {
                    {
                        if (pagerState.currentPage == 0) {
                            homeNavController.navigateUp()
                        }
                    }
                } else null,
            )
        },
        bottomBar = {
            MyBottomBar(
                currentRoute = currentRoute,
                onNavigationClick = { route ->
                    // Handle navigation based on target route
                    when (route) {
                        NavDestination.Home.route -> {
                            if (pagerState.currentPage == 0) {
                                homeNavController.popBackStack(
                                    route = NavDestination.Home.route,
                                    inclusive = false
                                )
                            } else {
                                coroutineScope.launch { pagerState.animateScrollToPage(0) }
                            }
                        }
                        NavDestination.Episodes.route -> {
                            coroutineScope.launch { pagerState.animateScrollToPage(1) }
                        }
                        NavDestination.Dynamic.route -> {
                            onDynamicClick()
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> {
                    // Home Nav Host
                    NavHost(
                        navController = homeNavController,
                        startDestination = NavDestination.Home.route
                    ) {
                        composable(NavDestination.Home.route) {
                            HomeScreen(
                                onCharacterSelected = { characterId ->
                                    homeNavController.navigate("character_details/$characterId")
                                }
                            )
                        }
                        composable(
                            route = "character_details/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val characterId: Int =
                                backStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterDetailsScreen(
                                characterId = characterId,
                                onEpisodeClicked = { episodeId ->
                                    homeNavController.navigate("character_episodes/$episodeId")
                                }
                            )
                        }
                        composable(
                            route = "character_episodes/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val characterId: Int =
                                backStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterEpisodeScreen(
                                characterId = characterId,
                                ktorClient = ktorClient
                            )
                        }
                    }
                }
                1 -> {
                    AllEpisodesScreen()
                    // Handle back press on Episodes tab to go to Home
                    BackHandler {
                        coroutineScope.launch { pagerState.animateScrollToPage(0) }
                    }
                }
            }
        }
    }
}