package com.example.spendless.domain.repository

import com.example.spendless.data.model.Username

interface LocalRepository {
    suspend fun saveUsername(username: Username)
    suspend fun isUsernameExists(enteredUsername: String): Boolean
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean
    suspend fun getStoredPin(enteredUsername: String): String?
}