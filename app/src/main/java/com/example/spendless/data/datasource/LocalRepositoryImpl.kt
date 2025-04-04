package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.local.LocalDataSource
import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): LocalRepository {
    override suspend fun saveUsername(username: Username) {
        localDataSource.saveUsername(username)
    }

    override suspend fun isUsernameExists(enteredUsername: String): Boolean {
        return localDataSource.isUsernameExists(enteredUsername = enteredUsername)
    }

    override suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean {
        return localDataSource.isValidUser(enteredUsername = enteredUsername, enteredPin = enteredPin)
    }

    override suspend fun getUserStoredPin(enteredUsername: String): String? {
        return localDataSource.getUserStoredPin(enteredUsername = enteredUsername)
    }

    override suspend fun getUserCurrentTime(enteredUsername: String): Long {
        return localDataSource.getUserCurrentTime(enteredUsername = enteredUsername)
    }

    override  fun getUserPreferencesFormat(enteredUsername: String): Flow<PreferencesFormat> {
        return localDataSource.getUserPreferencesFormat(enteredUsername = enteredUsername)
    }

    override suspend fun getUserSessionExpiryDuration(enteredUsername: String): SessionExpiryDuration {
        return localDataSource.getUserSessionExpiryDuration(enteredUsername = enteredUsername)
    }

    override suspend fun getUserLockedOutDuration(enteredUsername: String): LockedOutDuration {
        return localDataSource.getUserLockedOutDuration(enteredUsername = enteredUsername)
    }

    override suspend fun updateCurrentTime(
        enteredUsername: String,
        currentTime: Long
    ) {
        localDataSource.updateCurrentTime(enteredUsername = enteredUsername, currentTime = currentTime)
    }

    override suspend fun updatePreferencesFormat(
        enteredUsername: String,
        preferencesFormat: PreferencesFormat
    ) {
        localDataSource.updatePreferencesFormat(enteredUsername = enteredUsername, preferencesFormat = preferencesFormat)
    }

    override suspend fun updateSessionExpiryDuration(
        enteredUsername: String,
        sessionExpiryDuration: SessionExpiryDuration
    ) {
        localDataSource.updateSessionExpiryDuration(enteredUsername = enteredUsername, sessionExpiryDuration = sessionExpiryDuration)
    }

    override suspend fun updateLockedOutDuration(
        enteredUsername: String,
        lockedOutDuration: LockedOutDuration
    ) {
        localDataSource.updateLockedOutDuration(enteredUsername = enteredUsername, lockedOutDuration = lockedOutDuration)
    }
}