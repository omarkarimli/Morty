package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.core.commonui.CharacterImage
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.network.models.domain.Character

@Composable
fun CharacterGridItem(
    modifier: Modifier,
    character: Character,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
    ){
        Box {
            CharacterImage(imageUrl = character.imageUrl)
            CharacterStatusCircle(
                modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                status = character.status
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = character.name,
            style = AppTypography.titleMedium
        )
    }
}