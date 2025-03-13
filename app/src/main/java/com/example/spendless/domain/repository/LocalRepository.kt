package com.example.spendless.domain.repository

import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun saveUsername(username: Username)
    suspend fun getAllUsername(): Flow<List<Username>>
}