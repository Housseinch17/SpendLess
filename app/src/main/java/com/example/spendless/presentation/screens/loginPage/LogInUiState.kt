package com.example.spendless.presentation.screens.loginPage

import com.example.spendless.data.model.Username

data class LogInUiState(
    val username: String = "",
    val pin: String = "",
    val usernameCreated: List<Username> = emptyList(),
    )
