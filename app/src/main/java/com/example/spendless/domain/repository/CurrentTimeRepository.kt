package com.example.spendless.domain.repository

import com.example.spendless.data.model.SessionExpiryDuration

interface CurrentTimeRepository {
    fun getCurrentTime(): Long
    fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long
}