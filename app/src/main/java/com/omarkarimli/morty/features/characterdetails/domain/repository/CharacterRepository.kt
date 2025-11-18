package com.omarkarimli.morty.features.characterdetails.domain.repository

import com.omarkarimli.network.KtorClient
import com.omarkarimli.network.models.domain.Character
import com.omarkarimli.network.models.domain.CharacterPage

interface CharacterRepository {
    suspend fun fetchCharacterPage(
        page: Int,
        params: Map<String, String> = emptyMap()
    ): KtorClient.ApiOperation<CharacterPage>

    suspend fun fetchCharacter(characterId: Int): KtorClient.ApiOperation<Character>

    suspend fun fetchAllCharactersByName(searchQuery: String): KtorClient.ApiOperation<List<Character>>
}