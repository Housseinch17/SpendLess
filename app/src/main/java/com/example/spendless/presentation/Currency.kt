package com.example.spendless.presentation

import com.example.spendless.presentation.util.Utils.currencyList
import kotlinx.serialization.Serializable

val firstCurrency = currencyList.first()

@Serializable
data class Currency(
    val symbol: String = firstCurrency.symbol,
    val name: String = firstCurrency.name
)
