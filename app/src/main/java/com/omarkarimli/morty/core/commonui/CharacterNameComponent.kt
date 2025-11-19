package com.omarkarimli.morty.core.commonui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.omarkarimli.morty.ui.theme.AppTypography

@Composable
fun CharacterNameComponent(name: String) {
    Text(
        text = name,
        style = AppTypography.titleLarge
    )
}