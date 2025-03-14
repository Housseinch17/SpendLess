package com.example.spendless.presentation.screens.registerPage

import com.example.spendless.data.model.Username

data class RegisterUiState(
    val usernameValue: String = "",
    val usernameCreated: List<Username> = emptyList(),
    val isNextEnabled: Boolean = true,
)
