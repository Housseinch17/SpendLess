package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.currentTime.TimeDataSource
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.domain.repository.TimeRepository
import javax.inject.Inject

class TimeRepositoryImpl @Inject constructor(
    private val timeDataSource: TimeDataSource,
): TimeRepository {
    override fun getCurrentTime(): Long {
        return timeDataSource.getCurrentTime()
    }

    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long {
        return timeDataSource.updateSessionExpiryDuration(sessionExpiryDuration = sessionExpiryDuration)
    }
}