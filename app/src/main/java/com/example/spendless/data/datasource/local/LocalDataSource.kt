package com.example.spendless.data.datasource.local

import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveUsername(username: Username)
    suspend fun isUsernameExists(enteredUsername: String): Boolean
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean
    suspend fun getUserStoredPin(enteredUsername: String): String?
    suspend fun getUserCurrentTime(enteredUsername: String): Long
    fun getUserPreferencesFormat(enteredUsername: String): Flow<PreferencesFormat>
    suspend fun getUserSessionExpiryDuration(enteredUsername: String): SessionExpiryDuration
    suspend fun getUserLockedOutDuration(enteredUsername: String): LockedOutDuration
    suspend fun updateCurrentTime(enteredUsername: String, currentTime: Long)
    suspend fun updatePreferencesFormat(enteredUsername: String, preferencesFormat: PreferencesFormat)
    suspend fun updateSessionExpiryDuration(enteredUsername: String, sessionExpiryDuration: SessionExpiryDuration)
    suspend fun updateLockedOutDuration(enteredUsername: String, lockedOutDuration: LockedOutDuration)

}