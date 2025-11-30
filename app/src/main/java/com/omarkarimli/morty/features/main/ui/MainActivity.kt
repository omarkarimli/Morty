package com.omarkarimli.morty.features.main.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.omarkarimli.morty.R
import com.omarkarimli.morty.core.navigation.AppNavigation
import com.omarkarimli.morty.ui.theme.AppTheme
import com.omarkarimli.network.KtorClient
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val ktorClient: KtorClient by inject()
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Morty)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()
            .apply {
                setKeepOnScreenCondition {
                    viewModel.isLoading.value
                }
            }
        setContent {
            AppTheme {
                AppNavigation(ktorClient = ktorClient)
            }
        }
    }
}