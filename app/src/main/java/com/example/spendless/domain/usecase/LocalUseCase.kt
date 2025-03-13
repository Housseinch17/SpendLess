package com.example.spendless.domain.usecase

import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

interface LocalUseCase {
    suspend fun saveUsername(username: Username)
    suspend fun getAllUsername(): Flow<List<Username>>
}