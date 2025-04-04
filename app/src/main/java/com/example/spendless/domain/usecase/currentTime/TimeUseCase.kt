package com.example.spendless.domain.usecase.currentTime

import com.example.spendless.data.model.SessionExpiryDuration

interface TimeUseCase {
    fun getCurrentTime(): Long
    fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long
}