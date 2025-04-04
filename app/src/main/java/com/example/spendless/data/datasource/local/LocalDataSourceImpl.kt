package com.example.spendless.data.datasource.local

import android.util.Log
import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import com.example.spendless.data.roomdb.UsernameDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalDataSourceImpl @Inject constructor(
    @Named("DispatchersIO")
    private val coroutineDispatcher: CoroutineDispatcher,
    private val usernameDAO: UsernameDAO,
) : LocalDataSource {
    override suspend fun saveUsername(username: Username): Unit = withContext(coroutineDispatcher) {
        try {
            usernameDAO.saveUsername(username)
            Log.d("MyTag", "saveUsername successfully completed")
        } catch (e: Exception) {
            Log.d("MyTag", "saveUsername failed  ${e.message}")
        }
    }

    override suspend fun isUsernameExists(enteredUsername: String): Boolean =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.isUsernameExists(enteredUsername = enteredUsername)
        }

    override suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.isValidUser(
                enteredUsername = enteredUsername,
                enteredPin = enteredPin
            )
        }

    override suspend fun getUserStoredPin(enteredUsername: String): String? =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.getUserStoredPin(enteredUsername = enteredUsername)
        }

    override suspend fun getUserCurrentTime(enteredUsername: String): Long =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.getUserCurrentTime(enteredUsername = enteredUsername)
        }

    override  fun getUserPreferencesFormat(enteredUsername: String): Flow<PreferencesFormat>{
            return usernameDAO.getUserPreferencesFormat(enteredUsername = enteredUsername)
        }

    override suspend fun getUserSessionExpiryDuration(enteredUsername: String): SessionExpiryDuration =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.getUserSessionExpiryDuration(enteredUsername = enteredUsername)
        }

    override suspend fun getUserLockedOutDuration(enteredUsername: String): LockedOutDuration =
        withContext(coroutineDispatcher) {
            return@withContext usernameDAO.getUserLockedOutDuration(enteredUsername = enteredUsername)
        }

    override suspend fun updateCurrentTime(
        enteredUsername: String,
        currentTime: Long
    ) = withContext(coroutineDispatcher) {
        usernameDAO.updateCurrentTime(enteredUsername = enteredUsername, currentTime = currentTime)
    }

    override suspend fun updatePreferencesFormat(
        enteredUsername: String,
        preferencesFormat: PreferencesFormat
    ) = withContext(coroutineDispatcher) {
        usernameDAO.updatePreferencesFormat(
            enteredUsername = enteredUsername,
            preferencesFormat = preferencesFormat
        )
    }

    override suspend fun updateSessionExpiryDuration(
        enteredUsername: String,
        sessionExpiryDuration: SessionExpiryDuration
    ) = withContext(coroutineDispatcher) {
        usernameDAO.updateSessionExpiryDuration(
            enteredUsername = enteredUsername,
            sessionExpiryDuration = sessionExpiryDuration
        )
    }

    override suspend fun updateLockedOutDuration(
        enteredUsername: String,
        lockedOutDuration: LockedOutDuration
    ) = withContext(coroutineDispatcher) {
        usernameDAO.updateLockedOutDuration(
            enteredUsername = enteredUsername,
            lockedOutDuration = lockedOutDuration
        )
    }

}