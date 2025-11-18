package com.omarkarimli.morty.core.di

import com.omarkarimli.morty.features.allepisodes.data.repository.EpisodesRepositoryImpl
import com.omarkarimli.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.omarkarimli.morty.features.characterdetails.data.repository.CharacterRepositoryImpl
import com.omarkarimli.morty.features.characterdetails.domain.repository.CharacterRepository
import com.omarkarimli.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCharacterRepository(ktorClient: KtorClient): CharacterRepository {
        return CharacterRepositoryImpl(ktorClient)
    }

    @Provides
    @Singleton
    fun providesEpisodeRepository(ktorClient: KtorClient): EpisodesRepository {
        return EpisodesRepositoryImpl(ktorClient)

    }
}