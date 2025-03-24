package com.example.spendless.domain.usecase.currentTime

import com.example.spendless.data.model.SessionExpiryDuration

interface CurrentTimeUseCase {
    fun getCurrentTime(): String
    fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): String
}