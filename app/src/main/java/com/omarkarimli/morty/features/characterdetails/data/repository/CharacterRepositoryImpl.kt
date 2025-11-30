package com.omarkarimli.morty.features.characterdetails.data.repository

import com.omarkarimli.morty.features.characterdetails.domain.repository.CharacterRepository
import com.omarkarimli.network.KtorClient
import com.omarkarimli.network.models.domain.Character
import com.omarkarimli.network.models.domain.CharacterPage

class CharacterRepositoryImpl(
    private val ktorClient: KtorClient
) : CharacterRepository {

    override suspend fun fetchCharacterPage(
        page: Int,
        params: Map<String, String>
    ): KtorClient.ApiOperation<CharacterPage> {
        return ktorClient.getCharacterByPage(pageNumber = page, queryParams = params)
    }

    override suspend fun fetchCharacter(characterId: Int): KtorClient.ApiOperation<Character> {
        return ktorClient.getCharacters(characterId)
    }

    override suspend fun fetchAllCharactersByName(searchQuery: String): KtorClient.ApiOperation<List<Character>> {
        return ktorClient.searchAllCharactersByName(searchQuery)
    }
}