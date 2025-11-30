package com.omarkarimli.dynamicfeature

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.omarkarimli.morty.ui.theme.AppTheme
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.MyTopBar
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.morty.ui.theme.Dimens

class DynamicFeatureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                DynamicFeatureScreen(activity = this)
            }
        }
    }
}

@Composable
fun DynamicFeatureScreen(activity: Activity) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MyTopBar(
                title = stringResource(R.string.title_dynamicfeature),
                showBackButton = true,
                onBackClick = {
                    activity.finish()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimens.dp16)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
            ) {
                Text(
                    text = stringResource(R.string.feature_new_features),
                    style = AppTypography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                )
                Text(
                    text = stringResource(R.string.feature_new_features_info),
                    style = AppTypography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                )
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large),
                painter = painterResource(id = R.drawable.i2),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(R.string.publisher),
                style = AppTypography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
