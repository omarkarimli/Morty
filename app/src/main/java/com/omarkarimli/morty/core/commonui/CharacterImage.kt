package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import com.omarkarimli.morty.R

@Composable
fun CharacterImage(
    shape: Shape = MaterialShapes.Slanted.toShape(),
    imageUrl: String
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape),
        model = imageUrl,
        contentDescription = stringResource(R.string.accessibility_character_image),
        contentScale = ContentScale.Crop,
        loading = {
            LoadingState()
        }
    )
}