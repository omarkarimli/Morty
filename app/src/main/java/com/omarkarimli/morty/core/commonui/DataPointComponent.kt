package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.omarkarimli.morty.ui.theme.AppTypography

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column {
        Text(
            text = dataPoint.title,
            style = AppTypography.titleMedium
        )
        Text(
            text = dataPoint.description,
            style = AppTypography.bodyMedium
        )
    }
}