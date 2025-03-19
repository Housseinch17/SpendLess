package com.example.spendless.presentation.navigation

import com.example.spendless.data.model.Username
import kotlinx.serialization.Serializable

sealed interface NavigationScreens {

    @Serializable
    data object RegisterPage: NavigationScreens

    @Serializable
    data class CreatePinPage(val user: String = ""): NavigationScreens

    @Serializable
    data class RepeatPinPage(val username: Username = Username()): NavigationScreens

    @Serializable
    data object LogInPage: NavigationScreens

    @Serializable
    data class OnboardingPreferencesPage(val username: Username = Username()): NavigationScreens

    @Serializable
    data class DashBoardingPage(val username: String = ""): NavigationScreens

}