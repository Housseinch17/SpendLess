package com.example.spendless.presentation.navigation

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.example.spendless.R
import com.example.spendless.data.model.Username
import com.example.spendless.presentation.screens.createPinPage.CreatePinActions
import com.example.spendless.presentation.screens.createPinPage.CreatePinEvents
import com.example.spendless.presentation.screens.createPinPage.CreatePinPage
import com.example.spendless.presentation.screens.createPinPage.CreatePinViewModel
import com.example.spendless.presentation.screens.loginPage.LogInEvents
import com.example.spendless.presentation.screens.loginPage.LogInPage
import com.example.spendless.presentation.screens.loginPage.LogInViewModel
import com.example.spendless.presentation.screens.onboardingPreferencesPage.OnboardingPreferencesActions
import com.example.spendless.presentation.screens.onboardingPreferencesPage.OnboardingPreferencesEvents
import com.example.spendless.presentation.screens.onboardingPreferencesPage.OnboardingPreferencesPage
import com.example.spendless.presentation.screens.onboardingPreferencesPage.OnboardingPreferencesViewModel
import com.example.spendless.presentation.screens.registerPage.RegisterEvents
import com.example.spendless.presentation.screens.registerPage.RegisterPage
import com.example.spendless.presentation.screens.registerPage.RegisterViewModel
import com.example.spendless.presentation.screens.repeatPinPage.RepeatPinActions
import com.example.spendless.presentation.screens.repeatPinPage.RepeatPinEvents
import com.example.spendless.presentation.screens.repeatPinPage.RepeatPinPage
import com.example.spendless.presentation.screens.repeatPinPage.RepeatPinViewModel
import com.example.spendless.presentation.screens.shared.SharedActions
import com.example.spendless.presentation.screens.shared.SharedUiState
import com.example.spendless.presentation.screens.shared.SharedViewModel
import com.example.spendless.presentation.theme.Schemes_Background
import com.example.spendless.presentation.theme.Schemes_Primary
import com.example.spendless.presentation.util.BannerHandler
import com.example.spendless.presentation.util.CustomNavType
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RestrictedApi")
@Composable
fun Navigation(
    modifier: Modifier,
    navHostController: NavHostController,
    startDestination: NavigationScreens,
    sharedViewModel: SharedViewModel,
    sharedUiState: SharedUiState,
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier.background(color = Schemes_Background),
        startDestination = startDestination,
        navController = navHostController
    ) {


        composable<NavigationScreens.RegisterPage> {
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val registerViewModel = hiltViewModel<RegisterViewModel>()
            val registerUiState by registerViewModel.registerUiState.collectAsStateWithLifecycle()

            //lifecycle to hide banner when pause/stop(navigating)
            val lifecycleOwner = LocalLifecycleOwner.current


            LaunchedEffect(registerViewModel.events) {
                registerViewModel.events.collect { events ->
                    when (events) {
                        RegisterEvents.NavigateToLogInPage -> {
                            navHostController.navigate(NavigationScreens.LogInPage)
                        }

                        is RegisterEvents.NavigateToCreatePinPage -> {
                            navHostController.navigate(NavigationScreens.CreatePinPage(user = events.username))
                        }

                        RegisterEvents.ShowBanner -> {
                            val bannerText = context.getString(R.string.username_already_taken)
                            sharedViewModel.onActions(SharedActions.ShowBanner(bannerText = bannerText))
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
                onActions = sharedViewModel::onActions,
                lifecycleOwner = lifecycleOwner
            )

            RegisterPage(
                modifier = Modifier.fillMaxSize(),
                state = registerUiState,
                onActions = registerViewModel::onActions
            )

        }

        composable<NavigationScreens.CreatePinPage> { entry ->
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val createPinViewModel = hiltViewModel<CreatePinViewModel>()
            val createPinUiState by createPinViewModel.createPinUiState.collectAsStateWithLifecycle()

            val args = entry.toRoute<NavigationScreens.CreatePinPage>()

            LaunchedEffect(Unit) {
                createPinViewModel.onActions(CreatePinActions.UpdateUsername(args.user))
            }

            LaunchedEffect(createPinViewModel.events) {
                createPinViewModel.events.collect { events ->
                    when (events) {
                        is CreatePinEvents.RepeatPinPage -> {
                            navHostController.navigate(NavigationScreens.RepeatPinPage(username = events.username))

                            //clear pin after navigate
                            createPinViewModel.onActions(CreatePinActions.ResetPin)
                        }

                        CreatePinEvents.NavigateBack -> navHostController.navigateUp()
                    }
                }
            }

            CreatePinPage(
                modifier = Modifier.fillMaxSize(),
                state = createPinUiState,
                onActions = createPinViewModel::onActions,
            )

        }

        composable<NavigationScreens.RepeatPinPage>(
            typeMap = mapOf(
                typeOf<Username>() to CustomNavType.username
            )
        ) { entry ->
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val args = entry.toRoute<NavigationScreens.RepeatPinPage>()

            LaunchedEffect(Unit) {
//                Log.d("MyTag", "pin is : ${args.username.pin}")
            }

            val repeatPinPageViewModel = hiltViewModel<RepeatPinViewModel>()
            val repeatPinUiState by repeatPinPageViewModel.repeatPinUiState.collectAsStateWithLifecycle()

            //lifecycle to hide banner when pause/stop(navigating)
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(Unit) {
                repeatPinPageViewModel.onActions(RepeatPinActions.UpdateUsername(args.username))
            }

            LaunchedEffect(repeatPinPageViewModel.events) {
                repeatPinPageViewModel.events.collect { events ->
                    when (events) {
                        RepeatPinEvents.NavigateBack -> navHostController.navigateUp()

                        is RepeatPinEvents.OnboardingPreferencesPage -> {
                            navHostController.navigate(
                                NavigationScreens.OnboardingPreferencesPage(
                                    username = events.username
                                )
                            ) {
                                //here if i want to popUpTo a data class
                                /*popUpTo<NavigationScreens.CreatePinPage> {
                                    inclusive = false
                                }*/
                                popUpTo(navHostController.currentDestination!!.route.toString()) {
                                    inclusive = true
                                }
                            }
                        }

                        RepeatPinEvents.ShowBanner -> {
                            val bannerText = context.getString(R.string.pins_doesnt_match)
                            sharedViewModel.onActions(SharedActions.ShowBanner(bannerText = bannerText))
                        }

                        is RepeatPinEvents.Error -> {
                            Toast.makeText(context, events.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            BannerHandler(
                onActions = sharedViewModel::onActions,
                lifecycleOwner = lifecycleOwner
            )


            RepeatPinPage(
                modifier = Modifier.fillMaxSize(),
                state = repeatPinUiState,
                onActions = repeatPinPageViewModel::onActions,
            )
        }

        composable<NavigationScreens.LogInPage> {
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val logInViewModel = hiltViewModel<LogInViewModel>()
            val logInUiState by logInViewModel.logInUiState.collectAsStateWithLifecycle()

            LaunchedEffect(logInViewModel.events) {
                logInViewModel.events.collect { events ->
                    when (events) {
                        LogInEvents.RegisterAccount -> navHostController.navigate(NavigationScreens.RegisterPage)
                        is LogInEvents.ShowError -> Toast.makeText(
                            context,
                            events.error,
                            Toast.LENGTH_LONG
                        ).show()

                        is LogInEvents.LoggedIn -> navHostController.navigate(
                            NavigationScreens.DashBoardingPage(
                                username = events.username
                            )
                        ) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            LogInPage(
                modifier = Modifier.fillMaxSize(),
                state = logInUiState,
                onActions = logInViewModel::onActions
            )
        }

        composable<NavigationScreens.OnboardingPreferencesPage>(
            typeMap = mapOf(
                typeOf<Username>() to CustomNavType.username
            )
        ) { entry ->
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val args = entry.toRoute<NavigationScreens.OnboardingPreferencesPage>()

            val onboardingPreferencesViewModel = hiltViewModel<OnboardingPreferencesViewModel>()
            val onboardingPreferencesUiState by onboardingPreferencesViewModel.onboardingPreferencesUiState.collectAsStateWithLifecycle()


            LaunchedEffect(Unit) {
                //updating username in OnBoardingPreferencesViewModel to save it locally
                onboardingPreferencesViewModel.onActions(
                    OnboardingPreferencesActions.UpdateUsername(
                        username = args.username
                    )
                )
            }

            LaunchedEffect(onboardingPreferencesViewModel.onboardingPreferencesEvents) {
                onboardingPreferencesViewModel.onboardingPreferencesEvents.collect { events ->
                    when (events) {
                        OnboardingPreferencesEvents.NavigateBack -> navHostController.navigateUp()
                        is OnboardingPreferencesEvents.ShowError -> Toast.makeText(
                            context, events.error, Toast.LENGTH_LONG
                        ).show()

                        is OnboardingPreferencesEvents.NavigateToDashBoard -> {
                            navHostController.navigate(NavigationScreens.DashBoardingPage(
                                username = events.username
                            )){
                                popUpTo(0){
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }

            OnboardingPreferencesPage(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                state = onboardingPreferencesUiState,
                onActions = onboardingPreferencesViewModel::onActions,
            )
        }

        composable<NavigationScreens.DashBoardingPage> { entry ->
            val backstackEntry = navHostController.currentBackStack.value
            Log.d("BackstackEntry", "$backstackEntry")

            val args = entry.toRoute<NavigationScreens.DashBoardingPage>()

            LaunchedEffect(Unit) {
                //updating username in sharedViewModel uiState
                sharedViewModel.onActions(SharedActions.UpdateUsername(username = args.username))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Schemes_Primary),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navHostController.navigate(NavigationScreens.LogInPage){
                            popUpTo(0){
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Navigate to home screen")
                }
            }
        }

        dialog<NavigationScreens.PromptPinPage>{

        }

    }



}
