package com.example.spendless.domain.usecase.local

import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

interface LocalUseCase {
    suspend fun saveUsername(username: Username)
    suspend fun isUsernameExists(enteredUsername: String): Boolean
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean
    suspend fun getStoredPin(enteredUsername: String): String?
    fun getSessionExpiryDuration(enteredUsername: String): Flow<SessionExpiryDuration>
    fun getLockedOutDuration(enteredUsername: String): Flow<LockedOutDuration>
}