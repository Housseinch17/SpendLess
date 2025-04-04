package com.example.spendless.domain.usecase.local

import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository,
) : LocalUseCase {
    override suspend fun saveUsername(username: Username) {
        return localRepository.saveUsername(username)
    }

    override suspend fun isUsernameExists(enteredUsername: String): Boolean {
        return localRepository.isUsernameExists(enteredUsername = enteredUsername)
    }

    override suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean {
        return localRepository.isValidUser(
            enteredUsername = enteredUsername,
            enteredPin = enteredPin
        )
    }

    override suspend fun getUserStoredPin(enteredUsername: String): String? {
        return localRepository.getUserStoredPin(enteredUsername = enteredUsername)
    }

    override suspend fun getUserCurrentTime(enteredUsername: String): Long {
        return localRepository.getUserCurrentTime(enteredUsername = enteredUsername)
    }

    override  fun getUserPreferencesFormat(enteredUsername: String): Flow<PreferencesFormat> {
        return localRepository.getUserPreferencesFormat(enteredUsername = enteredUsername)
    }

    override suspend fun getUserSessionExpiryDuration(enteredUsername: String): SessionExpiryDuration {
        return localRepository.getUserSessionExpiryDuration(enteredUsername = enteredUsername)
    }

    override suspend fun getUserLockedOutDuration(enteredUsername: String): LockedOutDuration {
        return localRepository.getUserLockedOutDuration(enteredUsername = enteredUsername)
    }

    override suspend fun updateCurrentTime(
        enteredUsername: String,
        currentTime: Long
    ) {
        localRepository.updateCurrentTime(enteredUsername = enteredUsername, currentTime = currentTime)
    }

    override suspend fun updatePreferencesFormat(
        enteredUsername: String,
        preferencesFormat: PreferencesFormat
    ) {
        localRepository.updatePreferencesFormat(enteredUsername = enteredUsername, preferencesFormat = preferencesFormat)
    }

    override suspend fun updateSessionExpiryDuration(
        enteredUsername: String,
        sessionExpiryDuration: SessionExpiryDuration
    ) {
        localRepository.updateSessionExpiryDuration(enteredUsername = enteredUsername, sessionExpiryDuration = sessionExpiryDuration)
    }

    override suspend fun updateLockedOutDuration(
        enteredUsername: String,
        lockedOutDuration: LockedOutDuration
    ) {
        localRepository.updateLockedOutDuration(enteredUsername = enteredUsername, lockedOutDuration = lockedOutDuration)
    }
}