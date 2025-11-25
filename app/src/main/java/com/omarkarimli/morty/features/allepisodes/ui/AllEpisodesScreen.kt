package com.omarkarimli.morty.features.allepisodes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.Composable
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.LoadingState
import com.omarkarimli.morty.core.commonui.EpisodeRowComponent
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.network.models.domain.Episode

sealed interface AllEpisodesUiState {
    object Error : AllEpisodesUiState
    object Loading : AllEpisodesUiState
    data class Success(val data: Map<String, List<Episode>>) : AllEpisodesUiState
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllEpisodesScreen(
    episodesViewModel: AllEpisodesViewModel = hiltViewModel()
) {
    val uiState by episodesViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        episodesViewModel.refreshAllEpisodes()
    }

    when (val state = uiState) {
        AllEpisodesUiState.Error -> {
            Text(text = stringResource(R.string.error_generic))
        }

        AllEpisodesUiState.Loading -> {
            LoadingState()
        }

        is AllEpisodesUiState.Success -> {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.data.forEach { mapEntry ->
                    stickyHeader(key = mapEntry.key) {
                        Header(
                            seasonName = mapEntry.key,
                            uniqueCharacterCount = mapEntry.value.flatMap {
                                it.characterIdsInEpisode
                            }.toSet().size
                        )
                        if (mapEntry.key != state.data.keys.last()) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                        }
                    }

                    mapEntry.value.forEach { episode ->
                        item(key = episode.id) { EpisodeRowComponent(episode = episode) }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(seasonName: String, uniqueCharacterCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = seasonName,
            style = AppTypography.headlineSmall
        )
        Text(
            text = stringResource(R.string.label_unique_characters, uniqueCharacterCount),
            style = AppTypography.titleLarge
        )
    }
}