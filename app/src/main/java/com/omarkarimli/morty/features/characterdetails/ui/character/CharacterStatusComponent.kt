package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.morty.ui.theme.Dimens
import com.omarkarimli.network.models.domain.CharacterStatus

@Composable
fun CharacterStatusComponent(characterStatus: CharacterStatus) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.dp4)
    ) {
        CharacterStatusCircle(
            status = characterStatus
        )
        Text(
            text = characterStatus.displayName,
            style = AppTypography.bodyMedium,
            softWrap = false,
            maxLines = 1
        )
    }
}