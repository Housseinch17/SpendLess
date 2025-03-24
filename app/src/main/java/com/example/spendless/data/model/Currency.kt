package com.example.spendless.data.model

import com.example.spendless.presentation.util.Utils.currencyList
import kotlinx.serialization.Serializable


@Serializable
data class Currency(
    val symbol: String = currencyList.first().symbol,
    val name: String = currencyList.first().name
)
