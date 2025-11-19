package com.omarkarimli.morty.features.characterdetails.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarkarimli.morty.R
import com.omarkarimli.morty.features.characterdetails.ui.character.CharacterDetailsNamePlateComponent
import com.omarkarimli.morty.core.commonui.CharacterImage
import com.omarkarimli.morty.core.commonui.DataPoint
import com.omarkarimli.morty.core.commonui.DataPointComponent
import com.omarkarimli.morty.core.commonui.LoadingState
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.network.models.domain.Character

sealed interface CharacterDetailsViewState {
    object Loading : CharacterDetailsViewState
    data class Error(val message: String) : CharacterDetailsViewState
    data class Success(
        val character: Character,
        val characterDataPoints: List<DataPoint>
    ) : CharacterDetailsViewState
}
@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClicked: (Int) -> Unit,
    onBackClicked: () -> Unit
){
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCharacter(characterId)
    })

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
            when(val viewState = state){
                CharacterDetailsViewState.Loading -> item { LoadingState() }
                is CharacterDetailsViewState.Error -> {
                    item {
                        Text(text = viewState.message)
                    }
                }
                is CharacterDetailsViewState.Success -> {
                    item {
                        CharacterDetailsNamePlateComponent(
                            name = viewState.character.name,
                            status = viewState.character.status
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    // Image
                    item {
                        CharacterImage(imageUrl = viewState.character.imageUrl)
                    }
                    // Data points
                    items(viewState.characterDataPoints){
                        DataPointComponent(dataPoint = it)
                    }
                    // Button
                    item {
                        Text(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onEpisodeClicked(characterId)
                                }
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.action_view_all_episodes),
                            style = AppTypography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                        )
                    }
                    item { Spacer(modifier = Modifier.height(64.dp)) }
                }
            }
        }
    }