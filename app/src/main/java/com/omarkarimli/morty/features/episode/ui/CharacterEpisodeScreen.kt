package com.omarkarimli.morty.features.episode.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.omarkarimli.network.KtorClient
import com.omarkarimli.network.models.domain.Episode
import kotlinx.coroutines.launch
import com.omarkarimli.network.models.domain.Character
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.CharacterImage
import com.omarkarimli.morty.core.commonui.CharacterNameComponent
import com.omarkarimli.morty.core.commonui.DataPoint
import com.omarkarimli.morty.core.commonui.DataPointComponent
import com.omarkarimli.morty.core.commonui.LoadingState
import com.omarkarimli.morty.core.commonui.EpisodeRowComponent
import com.omarkarimli.morty.ui.theme.AppTypography

sealed interface ScreenState {
    object Loading : ScreenState
    data class Success(val character: Character, val episodes: List<Episode>) : ScreenState
    data class Error(val message: String) : ScreenState
}

@Composable
fun CharacterEpisodeScreen(characterId: Int, ktorClient: KtorClient){
    var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Loading) }
    val errorMessage = stringResource(R.string.error_network)

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getCharacters(characterId).onSuccess { character ->
            launch {
                ktorClient.getEpisodes(character.episodeIds).onSuccess { episodes ->
                    screenState = ScreenState.Success(character, episodes)
                }.onFailure {
                    screenState = ScreenState.Error(message = errorMessage)
                }
            }
        }.onFailure {
            screenState = ScreenState.Error(message = errorMessage)
        }
    })

    when(val state = screenState){
        ScreenState.Loading -> {
            LoadingState()
        }
        is ScreenState.Success -> {
            MainScreen(
                character = state.character,
                episodes = state.episodes
            )
        }
        is ScreenState.Error -> {
            Text(
                text = state.message,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                textAlign = TextAlign.Center,
                fontSize = 26.sp
            )
        }
    }
}

@Composable
private fun MainScreen(character: Character, episodes: List<Episode>) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }

    Column {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 16.dp
            )
        ) {
            item { CharacterNameComponent(name = character.name) }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                LazyRow {
                    episodeBySeasonMap.forEach { mapEntry ->
                        item {
                            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                                val title = stringResource(R.string.label_season, mapEntry.key)
                                val description = stringResource(R.string.label_episode_count, mapEntry.value.size)
                                DataPointComponent(dataPoint = DataPoint(title, description))
                                if (mapEntry.key != episodeBySeasonMap.keys.last()) {
                                    VerticalDivider(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(horizontal = 16.dp),
                                        color = MaterialTheme.colorScheme.onSurface,
                                        thickness = 1.dp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { CharacterImage(imageUrl = character.imageUrl) }
            item { Spacer(modifier = Modifier.height(16.dp)) }

            episodeBySeasonMap.forEach { mapEntry ->
                stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(mapEntry.value) { episode ->
                    EpisodeRowComponent(episode = episode)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Text(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        text = stringResource(R.string.label_season, seasonNumber),
        style = AppTypography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
}
