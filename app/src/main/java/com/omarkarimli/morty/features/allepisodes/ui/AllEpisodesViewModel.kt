package com.omarkarimli.morty.features.allepisodes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarkarimli.morty.core.constants.Constants
import com.omarkarimli.morty.features.allepisodes.domain.repository.EpisodesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllEpisodesViewModel(
    private val repository: EpisodesRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<AllEpisodesUiState>(AllEpisodesUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun refreshAllEpisodes(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (forceRefresh) _uiState.update { AllEpisodesUiState.Loading }
        repository.fetchAllEpisodes()
            .onSuccess { episodeList ->
                _uiState.update {
                    AllEpisodesUiState.Success(
                        data = episodeList.groupBy {
                            it.seasonNumber.toString()
                        }.mapKeys {
                            Constants.SEASON_FORMAT.format(it.key)
                        }
                    )
                }
            }.onFailure {
                _uiState.update { AllEpisodesUiState.Error }
            }
    }

}