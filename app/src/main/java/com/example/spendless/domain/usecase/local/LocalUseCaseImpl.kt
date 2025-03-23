package com.example.spendless.domain.usecase.local

import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
import javax.inject.Inject

class LocalUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository,
): LocalUseCase {
    override suspend fun saveUsername(username: Username) {
        return localRepository.saveUsername(username)
    }

    override suspend fun isUsernameExists(enteredUsername: String): Boolean {
        return localRepository.isUsernameExists(enteredUsername = enteredUsername)
    }

    override suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean {
        return localRepository.isValidUser(enteredUsername = enteredUsername, enteredPin = enteredPin)
    }

    override suspend fun getStoredPin(enteredUsername: String): String? {
        return localRepository.getStoredPin(enteredUsername = enteredUsername)
    }
}