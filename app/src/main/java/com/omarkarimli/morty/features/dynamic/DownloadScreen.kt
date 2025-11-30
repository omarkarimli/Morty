package com.omarkarimli.morty.features.dynamic

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Downloading
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.omarkarimli.morty.core.commonui.MyTopBar
import com.omarkarimli.morty.core.commonui.WideButton
import com.omarkarimli.morty.core.constants.Constants
import com.omarkarimli.morty.ui.theme.AppTypography
import com.omarkarimli.morty.ui.theme.Dimens

@Composable
fun DownloadScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val splitInstallManager = remember { SplitInstallManagerFactory.create(context) }
    var progress by remember { mutableFloatStateOf(0f) }
    var status by remember { mutableStateOf("Idle") }
    var sessionId by remember { mutableIntStateOf(0) }
    var isDownloading by remember { mutableStateOf(false) }
    var isInstalled by remember { mutableStateOf(false) }

    val listener = remember {
        SplitInstallStateUpdatedListener { state ->
            if (state.sessionId() == sessionId) {
                when (state.status()) {
                    SplitInstallSessionStatus.DOWNLOADING -> {
                        status = "Downloading"
                        val totalBytes = state.totalBytesToDownload()
                        val progressBytes = state.bytesDownloaded()
                        progress = if (totalBytes > 0) progressBytes.toFloat() / totalBytes else 0f
                    }
                    SplitInstallSessionStatus.INSTALLING -> {
                        status = "Installing"
                        progress = 1f
                    }
                    SplitInstallSessionStatus.INSTALLED -> {
                        status = "Installed"
                        isDownloading = false
                        isInstalled = true
                    }
                    SplitInstallSessionStatus.FAILED -> {
                        status = "Failed"
                        isDownloading = false
                    }
                    SplitInstallSessionStatus.CANCELED -> {
                        status = "Canceled"
                        isDownloading = false
                    }
                    else -> {
                        // Other statuses
                    }
                }
            }
        }
    }

    DisposableEffect(splitInstallManager) {
        splitInstallManager.registerListener(listener)

        if (splitInstallManager.installedModules.contains("dynamicfeature")) {
            status = "Installed"
            isInstalled = true
        } else {
            // Start download immediately when screen opens
            val request = SplitInstallRequest.newBuilder()
                .addModule("dynamicfeature")
                .build()

            splitInstallManager.startInstall(request)
                .addOnSuccessListener { id ->
                    sessionId = id
                    isDownloading = true
                    status = "Starting Download..."
                }
                .addOnFailureListener { exception ->
                    status = "Error: ${exception.message}"
                    isDownloading = false
                }
        }

        onDispose {
            splitInstallManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Dynamic Download",
                showBackButton = true,
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        val text =
            if (isDownloading) "Cancel"
            else "Confirm"
        val icon =
            if (isInstalled) Icons.Rounded.Check
            else Icons.Rounded.Pause
        val onClick = {
            if (isDownloading) {
                splitInstallManager.cancelInstall(sessionId)
            } else {
                val intent = Intent().setClassName(context.packageName, Constants.DYNAMIC_FEATURE_CLASS_NAME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(Dimens.dp100),
                imageVector = Icons.Rounded.Downloading,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(Dimens.dp16),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = status,
                        style = AppTypography.titleLarge
                    )
                    Icon(
                        modifier = Modifier.padding(start = Dimens.dp8),
                        imageVector = icon,
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.height(Dimens.dp16))
                LinearWavyProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { progress }
                )
                Spacer(modifier = Modifier.height(Dimens.dp16))
                WideButton(
                    onClick = { onClick() },
                    text = text
                )
            }
        }
    }
}