package com.example.spendless.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LockedOutDuration(
    val isSeconds: Boolean = true,
    val lockedOutDuration: Long = 15
)
