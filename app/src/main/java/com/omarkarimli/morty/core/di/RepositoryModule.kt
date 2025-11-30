package com.omarkarimli.morty.core.di

import com.omarkarimli.morty.features.allepisodes.data.repository.EpisodesRepositoryImpl
import com.omarkarimli.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.omarkarimli.morty.features.characterdetails.data.repository.CharacterRepositoryImpl
import com.omarkarimli.morty.features.characterdetails.domain.repository.CharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<EpisodesRepository> { EpisodesRepositoryImpl(get()) }
}