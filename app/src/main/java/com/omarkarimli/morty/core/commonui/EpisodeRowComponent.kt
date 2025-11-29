package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.network.models.domain.Episode

@Composable
fun EpisodeRowComponent(episode: Episode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = stringResource(R.string.preview_data_point_episode_title)
                    + " "
                    + episode.episodeNumber.toString(),
            style = AppTypography.titleSmall
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