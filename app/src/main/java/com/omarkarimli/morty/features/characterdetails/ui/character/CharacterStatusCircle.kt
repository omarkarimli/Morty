package com.omarkarimli.morty.features.characterdetails.ui.character

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omarkarimli.network.models.domain.CharacterStatus
import com.omarkarimli.morty.core.utils.asColor

@Composable
fun CharacterStatusCircle(
    modifier: Modifier = Modifier,
    status: CharacterStatus
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = CircleShape
            )
            .size(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .background(color = status.asColor(), shape = CircleShape)
        )
    }
}