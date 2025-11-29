package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.omarkarimli.morty.ui.theme.Dimens
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingState() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        repeat(20) {
            LoadingItem()
        }
    }
}

@Composable
private fun LoadingItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.dp16),
        horizontalArrangement = Arrangement.spacedBy(Dimens.dp16)
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.dp100)
                .shimmer()
                .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.large)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.dp8),
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp20)
                    .shimmer()
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(Dimens.dp20)
                    .shimmer()
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
            )
        }
    }
}
