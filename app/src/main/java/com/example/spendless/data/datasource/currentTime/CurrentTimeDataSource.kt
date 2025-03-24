package com.example.spendless.data.datasource.currentTime

import com.example.spendless.data.model.SessionExpiryDuration

interface CurrentTimeDataSource {
    fun getCurrentTime(): Long
    fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long
}