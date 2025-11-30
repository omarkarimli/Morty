package com.omarkarimli.morty.core.di

import com.omarkarimli.morty.features.allcharacters.ui.HomeScreenViewModel
import com.omarkarimli.morty.features.allepisodes.ui.AllEpisodesViewModel
import com.omarkarimli.morty.features.characterdetails.ui.CharacterDetailsViewModel
import com.omarkarimli.morty.features.main.ui.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharacterDetailsViewModel(get()) }
    viewModel { AllEpisodesViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { MainViewModel() }
}