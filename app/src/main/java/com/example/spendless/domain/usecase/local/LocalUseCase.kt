package com.example.spendless.domain.usecase.local

import com.example.spendless.data.model.Username

interface LocalUseCase {
    suspend fun saveUsername(username: Username)
    suspend fun isUsernameExists(enteredUsername: String): Boolean
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean
}