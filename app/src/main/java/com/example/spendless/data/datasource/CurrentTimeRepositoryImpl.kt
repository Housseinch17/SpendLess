package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.currentTime.CurrentTimeDataSource
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.domain.repository.CurrentTimeRepository
import javax.inject.Inject

class CurrentTimeRepositoryImpl @Inject constructor(
    private val currentTimeDataSource: CurrentTimeDataSource,
): CurrentTimeRepository {
    override fun getCurrentTime(): String {
        return currentTimeDataSource.getCurrentTime()
    }

    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): String {
        return currentTimeDataSource.updateSessionExpiryDuration(sessionExpiryDuration = sessionExpiryDuration)
    }
}