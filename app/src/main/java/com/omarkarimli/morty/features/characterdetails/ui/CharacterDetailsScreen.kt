package com.omarkarimli.morty.features.characterdetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import com.omarkarimli.morty.R
import com.omarkarimli.morty.features.characterdetails.ui.character.CharacterDetailsNamePlateComponent
import com.omarkarimli.morty.core.commonui.CharacterImage
import com.omarkarimli.morty.core.commonui.DataPoint
import com.omarkarimli.morty.core.commonui.DataPointComponent
import com.omarkarimli.morty.core.commonui.LoadingState
import com.omarkarimli.morty.core.commonui.WideButton
import com.omarkarimli.morty.ui.theme.Dimens
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
    viewModel: CharacterDetailsViewModel = koinViewModel(),
    onEpisodeClicked: (Int) -> Unit
){
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCharacter(characterId)
    })

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.dp16),
        verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
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
                        Spacer(modifier = Modifier.height(Dimens.dp8))
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
                        WideButton(
                            text = stringResource(R.string.action_view_all_episodes),
                            onClick = {
                                onEpisodeClicked(characterId)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(Dimens.dp64)) }
                }
            }
        }
    }