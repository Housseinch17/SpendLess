package com.example.spendless.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationScreens {

    @Serializable
    data object LogInPage: NavigationScreens

    @Serializable
    data object RegisterPage: NavigationScreens

    @Serializable
    data class PinPage(val username: String): NavigationScreens

    @Serializable
    data object RepeatPinPage: NavigationScreens

}