package com.example.spendless.presentation.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.spendless.R
import com.example.spendless.presentation.screens.createPinPage.CreatePinEvents
import com.example.spendless.presentation.screens.createPinPage.CreatePinViewModel
import com.example.spendless.presentation.screens.registerPage.RegisterEvents
import com.example.spendless.presentation.screens.registerPage.RegisterPage
import com.example.spendless.presentation.screens.registerPage.RegisterViewModel
import com.example.spendless.presentation.screens.shared.SharedActions
import com.example.spendless.presentation.util.BannerHandler

@Composable
fun Navigation(
    modifier: Modifier,
    navHostController: NavHostController,
    startDestination: NavigationScreens,
    onActions: (SharedActions) -> Unit,
) {

    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        startDestination = startDestination,
        navController = navHostController
    ) {

        composable<NavigationScreens.RegisterPage> {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            val registerUiState by registerViewModel.registerUiState.collectAsStateWithLifecycle()

            //lifecycle to hide banner when pause/stop(navigating
            val lifecycleOwner = LocalLifecycleOwner.current


            LaunchedEffect(registerViewModel.events) {
                registerViewModel.events.collect { events ->
                    when (events) {
                        RegisterEvents.NavigateToLogInPage -> {
                            navHostController.navigate(NavigationScreens.LogInPage)
                        }

                        is RegisterEvents.NavigateToPinPage -> {
                            navHostController.navigate(NavigationScreens.PinPage(username = events.username))
                        }

                        RegisterEvents.ShowBanner -> {
                            val bannerText = context.getString(R.string.username_already_taken)
                            onActions(SharedActions.ShowBanner(bannerText = bannerText))
                        }

                        RegisterEvents.ShowError -> Toast.makeText(
                            context,
                            "Username should be 3-14 characters!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            BannerHandler(
                onActions = onActions,
                lifecycleOwner = lifecycleOwner
            )

            RegisterPage(
                modifier = Modifier.fillMaxSize(),
                registerUiState = registerUiState,
                registerActions = registerViewModel::onActions
            )

        }

        composable<NavigationScreens.PinPage> { entry ->
            val args = entry.toRoute<NavigationScreens.PinPage>()
//            Log.d("MyTag", args.username)

            val createPinViewModel = hiltViewModel<CreatePinViewModel>()
            val createPinUiState by createPinViewModel.createPinUiState.collectAsStateWithLifecycle()

            LaunchedEffect(createPinViewModel.events) {
                createPinViewModel.events.collect { events ->
                    when (events) {
                        is CreatePinEvents.RepeatPinPage -> {
                            navHostController.navigate(NavigationScreens.RepeatPinPage)
                        }
                    }

                }
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "PinPage")
            }
        }


        composable<NavigationScreens.RepeatPinPage> { entry ->
            val args = entry.toRoute<NavigationScreens.RepeatPinPage>()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "RepeatPinPage")
            }
        }

        composable<NavigationScreens.LogInPage> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "LogInPage")
            }
        }
    }
}