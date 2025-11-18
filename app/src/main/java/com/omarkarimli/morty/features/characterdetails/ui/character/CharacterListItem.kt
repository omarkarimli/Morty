package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.R
import com.omarkarimli.network.models.domain.Character
import com.omarkarimli.morty.core.commonui.CharacterImage
import com.omarkarimli.morty.core.commonui.DataPointComponent
import com.omarkarimli.morty.core.commonui.DataPoint

@Composable
fun CharacterListItem(
    modifier: Modifier = Modifier,
    character: Character,
    characterDataPoints: List<DataPoint>,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(140.dp)
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, MaterialTheme.colorScheme.primaryContainer)),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box {
            CharacterImage(
                imageUrl = character.imageUrl,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            CharacterStatusCircle(
                status = character.status,
                modifier = Modifier.padding(start = 6.dp, top = 6.dp)
            )
        }
        val nameTitle = stringResource(R.string.preview_data_point_name_title)
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
                items(
                    items = listOf(
                        DataPoint(
                            title = nameTitle,
                            description = character.name
                        )
                    ) + characterDataPoints,
                    key = { it.hashCode() }
                ) { dataPoint ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        DataPointComponent(dataPoint = dataPoint)
                    }
                }
            })
    }
}