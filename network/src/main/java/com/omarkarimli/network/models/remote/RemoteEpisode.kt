package com.omarkarimli.network.models.remote

import kotlinx.serialization.Serializable
import com.omarkarimli.network.models.domain.Episode
import kotlinx.serialization.SerialName

@Serializable
data class RemoteEpisode(
    val id: Int,
    val name: String,
    val episode: String,
    @SerialName("air_date")
    val airDate: String,
    val characters: List<String>
)

fun RemoteEpisode.toDomainEpisode(): Episode {
    return Episode(
        id = id,
        name = name,
        seasonNumber = episode.filter { it.isDigit() }.take(2).toInt(),
        episodeNumber = episode.filter { it.isDigit() }.takeLast(2).toInt(),
        airDate = airDate,
        characterIdsInEpisode = characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1).toInt()
        }
    )
}
