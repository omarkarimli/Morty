package com.omarkarimli.morty.features.allcharacters.ui

import com.omarkarimli.network.models.domain.Character

sealed interface HomeScreenViewState {
    object Loading : HomeScreenViewState
    data class GridDisplay(
        val characters: List<Character> = emptyList()
    ) : HomeScreenViewState
}