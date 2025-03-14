package com.example.spendless.data.datasource.local

import com.example.spendless.data.model.Username

interface LocalDataSource {
    suspend fun saveUsername(username: Username)
    suspend fun isUsernameExists(enteredUsername: String): Boolean
    suspend fun isValidUser(enteredUsername: String, enteredPin: Int): Boolean
}