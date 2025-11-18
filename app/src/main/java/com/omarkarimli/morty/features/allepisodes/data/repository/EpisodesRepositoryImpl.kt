package com.omarkarimli.morty.features.allepisodes.data.repository

import com.omarkarimli.morty.features.allepisodes.domain.repository.EpisodesRepository
import com.omarkarimli.network.KtorClient
import com.omarkarimli.network.models.domain.Episode
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor
    (private val ktorClient: KtorClient) : EpisodesRepository {

    override suspend fun fetchAllEpisodes(): KtorClient.ApiOperation<List<Episode>> =
        ktorClient.getAllEpisodes()

    override suspend fun getEpisode(episodeId: Int) = ktorClient.getEpisode(episodeId)


}