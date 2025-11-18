package com.omarkarimli.morty.core.commonui

import androidx.compose.runtime.Composable
import com.omarkarimli.network.models.domain.Episode
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.ui.theme.AppTypography

@Composable
fun EpisodeRowComponent(episode: Episode) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DataPointComponent(
            dataPoint = DataPoint(
                title = stringResource(R.string.preview_data_point_episode_title),
                description = episode.episodeNumber.toString()
            )
        )
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = episode.name,
                style = AppTypography.titleSmall,
                textAlign = TextAlign.End,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = episode.airDate,
                style = AppTypography.bodySmall,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
private fun EpisodeRowComponentPreview() {
    val episode = Episode(
        id = 28,
        name = stringResource(R.string.preview_episode_name),
        seasonNumber = 3,
        episodeNumber = 7,
        airDate = stringResource(R.string.preview_episode_air_date),
        characterIdsInEpisode = listOf(
            1,
            2,
            4,
            8,
            18,
            22,
            27,
            43,
            44,
            48,
            56,
            61,
            72,
            73,
            74,
            78,
            85,
            86,
            118,
            123,
            135,
            143,
            165,
            180,
            187,
            206,
            220,
            229,
            233,
            235,
            267,
            278,
            281,
            283,
            284,
            287,
            288,
            289,
            291,
            292,
            322,
            325,
            328,
            345,
            366,
            367,
            392,
            472,
            473,
            474,
            475,
            476,
            477,
            478,
            479,
            480,
            481,
            482,
            483,
            484,
            485,
            486,
            487,
            488,
            489
        )
    )

    EpisodeRowComponent(episode = episode)
}