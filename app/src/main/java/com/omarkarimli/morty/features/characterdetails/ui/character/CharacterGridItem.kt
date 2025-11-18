package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ){
        Box {
            CharacterImage(imageUrl = character.imageUrl)
            CharacterStatusCircle(
                status = character.status,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp)
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = character.name,
            style = AppTypography.bodyMedium
        )
    }
}