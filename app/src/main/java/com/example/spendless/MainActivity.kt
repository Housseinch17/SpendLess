package com.example.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.spendless.presentation.navigation.Navigation
import com.example.spendless.presentation.navigation.NavigationScreens
import com.example.spendless.presentation.screens.shared.SharedActions
import com.example.spendless.presentation.screens.shared.SharedViewModel
import com.example.spendless.presentation.theme.SpendLessTheme
import com.example.spendless.presentation.util.Banner
import com.example.spendless.presentation.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendLessTheme {
                val navController = rememberNavController()
                val sharedViewModel = hiltViewModel<SharedViewModel>()
                val sharedUiState by sharedViewModel.sharedUiState.collectAsStateWithLifecycle()

                LaunchedEffect(sharedUiState.showBanner) {
                    if (sharedUiState.showBanner) {
                        delay(Utils.BANNER_SHOW_TIME)
                        sharedViewModel.onActions(SharedActions.HideBanner)
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .imePadding(),
                    bottomBar = {
                        if (sharedUiState.showBanner) {
                            Banner(
                                bannerText = sharedUiState.bannerText,
                            )
                        }
                    }
                ) { innerPadding ->
                    Navigation(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navHostController = navController,
                        startDestination = NavigationScreens.RegisterPage,
                        sharedViewModel = sharedViewModel,
                        sharedUiState = sharedUiState
                    )
                }
            }
        }
    }
}
