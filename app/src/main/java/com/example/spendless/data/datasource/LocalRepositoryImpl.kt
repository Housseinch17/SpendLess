package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.local.LocalDataSource
import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
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

    override suspend fun getStoredPin(enteredUsername: String): String? {
        return localDataSource.getStoredPin(enteredUsername = enteredUsername)
    }
}