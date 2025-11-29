package com.omarkarimli.morty.features.dynamic

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

@Composable
fun DownloadScreen() {
    val context = LocalContext.current
    val splitInstallManager = remember { SplitInstallManagerFactory.create(context) }
    var progress by remember { androidx.compose.runtime.mutableFloatStateOf(0f) }
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

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Status: $status")
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isDownloading) {
            LinearProgressIndicator(progress = { progress })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                splitInstallManager.cancelInstall(sessionId)
            }) {
                Text("Cancel")
            }
        } else if (isInstalled) {
            Button(onClick = {
                val intent = Intent().setClassName(context.packageName, "com.omarkarimli.dynamicfeature.DynamicFeatureActivity")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }) {
                Text("Complete - Open Feature")
            }
        }
    }
}