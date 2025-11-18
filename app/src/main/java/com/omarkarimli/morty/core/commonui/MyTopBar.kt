package com.omarkarimli.morty.core.commonui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omarkarimli.morty.R
import com.omarkarimli.morty.ui.theme.RickTextPrimary

@Composable
fun MyTopBar(
    title: String = stringResource(R.string.app_title_default),
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            val titleDescription = stringResource(R.string.accessibility_app_title, title)
            Text(
                text = title,
                color = RickTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .semantics {
                        contentDescription = titleDescription
                    }
            )
        },
        navigationIcon = {
            if (showBackButton && onBackClick != null) {
                val backButtonDescription = stringResource(R.string.accessibility_navigate_back)
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(48.dp)
                        .semantics {
                            contentDescription = backButtonDescription
                        }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = RickTextPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}