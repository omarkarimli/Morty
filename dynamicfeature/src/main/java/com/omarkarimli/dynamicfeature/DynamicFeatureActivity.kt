package com.omarkarimli.dynamicfeature

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.omarkarimli.dynamicfeature.ui.theme.MortyTheme
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.commonui.MyTopBar
import com.omarkarimli.morty.core.commonui.WideButton
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.morty.ui.theme.Dimens

class DynamicFeatureActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MortyTheme {
                DynamicFeatureScreen(activity = this)
            }
        }
    }
}

@Composable
fun DynamicFeatureScreen(activity: Activity) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(
                title = "Dynamic Feature",
                showBackButton = true,
                onBackClick = {
                    activity.finish()
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimens.dp16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(vertical = Dimens.dp8),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "New Features Here",
                    style = AppTypography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurface),
                    textAlign = TextAlign.Center
                )
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large),
                painter = painterResource(id = R.drawable.i1),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            WideButton(
                text = "Completed",
                onClick = {}
            )
        }
    }
}