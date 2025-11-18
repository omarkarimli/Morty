package com.omarkarimli.morty.features.allepisodes.domain.repository

import com.omarkarimli.network.KtorClient
import com.omarkarimli.network.models.domain.Episode

interface EpisodesRepository {

    suspend fun fetchAllEpisodes(): KtorClient.ApiOperation<List<Episode>>

    suspend fun getEpisode(episodeId: Int): KtorClient.ApiOperation<Episode>
}