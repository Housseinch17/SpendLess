package com.example.spendless.data.datasource.currentTime

import android.annotation.SuppressLint
import android.util.Log
import com.example.spendless.data.model.SessionExpiryDuration
import java.time.LocalTime
import javax.inject.Inject

class TimeDataSourceImpl @Inject constructor() : TimeDataSource {

    @SuppressLint("NewApi")
    override fun getCurrentTime(): Long {
        val currentTime = LocalTime.now()
        // Get current time in milliseconds
        Log.d("MyTag","getCurrentTime: ${currentTime.toNanoOfDay()}")
        return currentTime.toNanoOfDay() / 1_000_000  // Converts to milliseconds
    }

    @SuppressLint("NewApi")
    override fun updateSessionExpiryDuration(sessionExpiryDuration: SessionExpiryDuration): Long {
        val currentTime = LocalTime.now()
        val newTime = if (sessionExpiryDuration.isMinute) {
            currentTime.plusMinutes(sessionExpiryDuration.sessionExpiryDuration)
        } else {
            currentTime.plusHours(sessionExpiryDuration.sessionExpiryDuration)
        }
        // Return the new time in milliseconds
        Log.d("MyTag","updateSession: ${newTime.toNanoOfDay()}")
        return newTime.toNanoOfDay() / 1_000_000  // Converts to milliseconds
    }

}