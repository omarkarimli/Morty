package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.utils.asColor
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.network.models.domain.CharacterStatus

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = characterStatus.asColor(),
                shape = MaterialTheme.shapes.medium
            )
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = stringResource(R.string.label_status, characterStatus.displayName),
            style = AppTypography.bodyMedium
        )
    }
}