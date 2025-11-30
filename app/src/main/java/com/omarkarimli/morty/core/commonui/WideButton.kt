package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.morty.ui.theme.Dimens
import java.util.Locale

@Composable
fun WideButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.onSurface,
    contentColor: Color = MaterialTheme.colorScheme.surface,
    enabled: Boolean = true,
    text: String = "Confirm",
    textStyle: TextStyle = AppTypography.titleMedium,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.dp48),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text.uppercase(Locale.ROOT),
            style = textStyle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            letterSpacing = Dimens.LetterSpacingButton
        )
    }
}