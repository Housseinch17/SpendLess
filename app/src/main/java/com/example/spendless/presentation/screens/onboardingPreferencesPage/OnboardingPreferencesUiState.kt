package com.example.spendless.presentation.screens.onboardingPreferencesPage

import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.Username

data class OnboardingPreferencesUiState(
    val user: Username = Username(),
    val price:  Double = 10382.45,
    val formattedPrice: String = "-$10,382.45",
    val preferencesFormat: PreferencesFormat = PreferencesFormat(),
    val isExpanded: Boolean = false,
    val hasSameOperator: Boolean = false
)



