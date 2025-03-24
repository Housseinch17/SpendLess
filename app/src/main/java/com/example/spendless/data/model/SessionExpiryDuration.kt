package com.example.spendless.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionExpiryDuration(
    val isMinute: Boolean = true,
    val sessionExpiryDuration: Long = 5,
)
