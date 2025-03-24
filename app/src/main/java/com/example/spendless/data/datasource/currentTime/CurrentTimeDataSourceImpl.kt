package com.example.spendless.data.datasource.currentTime

import android.annotation.SuppressLint
import com.example.spendless.data.model.SessionExpiryDuration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CurrentTimeDataSourceImpl @Inject constructor() : CurrentTimeDataSource {
    @SuppressLint("NewApi")
    override fun getCurrentTime(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return currentTime.format(formatter)
    }

    @SuppressLint("NewApi")
    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): String {
        val currentTime = LocalTime.now()
        //if minute add minutes if hour add hours
        // 02:56 time, 5min 03:01, 02:56 time, 1hour, 03:56
        val newTime = if (sessionExpiryDuration.isMinute) currentTime.plusMinutes(sessionExpiryDuration.sessionExpiryDuration) else currentTime.plusHours(sessionExpiryDuration.sessionExpiryDuration)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return newTime.format(formatter)
    }
}