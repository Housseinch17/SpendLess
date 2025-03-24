package com.example.spendless.domain.repository

import com.example.spendless.data.model.SessionExpiryDuration

interface CurrentTimeRepository {
    fun getCurrentTime(): String
    fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): String
}