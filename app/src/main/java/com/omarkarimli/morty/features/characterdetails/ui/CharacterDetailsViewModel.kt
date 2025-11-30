package com.omarkarimli.morty.features.characterdetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.DataPoint
import com.omarkarimli.morty.core.commonui.UiText
import com.omarkarimli.morty.features.characterdetails.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterRepository: CharacterRepository
): ViewModel(){

    private val _internalStorageFlow = MutableStateFlow<CharacterDetailsViewState>(
        value = CharacterDetailsViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update { return@update CharacterDetailsViewState.Loading }
        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            val dataPoints = buildList {
                add(DataPoint(UiText.StringResource(R.string.data_point_last_known_location), UiText.DynamicString(character.location.name)))
                add(DataPoint(UiText.StringResource(R.string.data_point_species), UiText.DynamicString(character.species)))
                add(DataPoint(UiText.StringResource(R.string.data_point_gender), UiText.DynamicString(character.gender.displayName)))
                character.type.takeIf { it.isNotEmpty() }?.let { type ->
                    add(DataPoint(UiText.StringResource(R.string.data_point_type), UiText.DynamicString(type)))
                }
                add(DataPoint(UiText.StringResource(R.string.data_point_origin), UiText.DynamicString(character.origin.name)))
                add(DataPoint(UiText.StringResource(R.string.data_point_episode_count), UiText.DynamicString(character.episodeIds.size.toString())))
            }
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Success(
                    character = character,
                    characterDataPoints = dataPoints
                )
            }
        }.onFailure { exception ->
            _internalStorageFlow.update {
                return@update CharacterDetailsViewState.Error(
                    message = UiText.DynamicString(exception.message ?: "Unknown error")
                )
            }
        }
    }

}