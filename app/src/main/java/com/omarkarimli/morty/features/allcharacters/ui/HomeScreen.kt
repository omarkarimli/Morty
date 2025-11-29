package com.omarkarimli.morty.features.allcharacters.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.omarkarimli.morty.features.characterdetails.ui.character.CharacterGridItem
import com.omarkarimli.morty.core.commonui.LoadingState
import com.omarkarimli.morty.ui.theme.Dimens

@Composable
fun HomeScreen(
    onCharacterSelected: (Int) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewModel, block = { viewModel.fetchInitialPage() })

    val scrollState = rememberLazyGridState()
    val fetchNextPage: Boolean by remember{
        derivedStateOf {
            val currentCharacterCount =
                (viewState as? HomeScreenViewState.GridDisplay)?.characters?.size
                    ?: return@derivedStateOf false
            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: return@derivedStateOf false
            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount - 10
        }
    }

    LaunchedEffect(key1 = fetchNextPage, block = {
        if (fetchNextPage) viewModel.fetchNextPage()
    })

    when(val state = viewState){
        HomeScreenViewState.Loading -> {
            LoadingState()
        }
        is HomeScreenViewState.GridDisplay ->{
            LazyVerticalGrid(
                state = scrollState,
                contentPadding = PaddingValues(all = Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp8),
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
                columns = GridCells.Fixed(2),
                content = {
                    items(
                        items = state.characters,
                        key = { it.id }
                    ) { character ->
                        CharacterGridItem(modifier = Modifier, character = character) {
                            onCharacterSelected(character.id)
                        }
                    }
                })
        }
    }
}