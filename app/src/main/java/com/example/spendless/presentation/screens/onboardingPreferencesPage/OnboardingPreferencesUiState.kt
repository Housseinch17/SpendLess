package com.example.spendless.presentation.screens.onboardingPreferencesPage

import com.example.spendless.data.model.Username
import com.example.spendless.presentation.Currency
import kotlinx.serialization.Serializable

data class OnboardingPreferencesUiState(
    val user: Username = Username(),
    val price:  Double = 10382.45,
    val formattedPrice: String = "-$10,382.45",
    val preferencesFormat: PreferencesFormat = PreferencesFormat(),
    val isExpanded: Boolean = false,
    val hasSameOperator: Boolean = false
)

@Serializable
data class PreferencesFormat(
    val expensesIsNegative: Boolean = true,
    val currency: Currency = Currency(),
    val decimalSeparator: String = "1.00",
    val thousandsSeparator: String = "1,000"
)



