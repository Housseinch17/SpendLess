package com.example.spendless.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PreferencesFormat(
    val expensesIsNegative: Boolean = true,
    val currency: Currency = Currency(),
    val decimalSeparator: String = "1.00",
    val thousandsSeparator: String = "1,000"
)
