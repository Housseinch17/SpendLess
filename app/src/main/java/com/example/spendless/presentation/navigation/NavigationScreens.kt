package com.example.spendless.presentation.navigation

import com.example.spendless.data.model.Username
import kotlinx.serialization.Serializable

sealed interface NavigationScreens {

    @Serializable
    data object LogInPage: NavigationScreens

    @Serializable
    data object RegisterPage: NavigationScreens

    @Serializable
    data class PinPage(val user: String): NavigationScreens

    @Serializable
    data class RepeatPinPage(val username: Username): NavigationScreens

}