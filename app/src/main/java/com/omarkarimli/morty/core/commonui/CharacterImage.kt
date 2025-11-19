package com.omarkarimli.morty.core.commonui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import com.omarkarimli.morty.R

@Composable
fun CharacterImage(
    modifier: Modifier = Modifier,
    imageUrl: String
){
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.accessibility_character_image),
        modifier = modifier,
        loading = {
            LoadingState()
        }
    )
}