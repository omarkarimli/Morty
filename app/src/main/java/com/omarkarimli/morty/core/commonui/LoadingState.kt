package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omarkarimli.morty.ui.theme.RickAction

private val defaultModifier = Modifier
    .fillMaxSize()
    .padding(all = 128.dp)

@Composable
fun LoadingState(modifier: Modifier = defaultModifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = RickAction
        )
    }
}