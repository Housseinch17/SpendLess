package com.example.spendless.data.datasource.local

import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveUsername(username: Username)
    suspend fun getAllUsername(): Flow<List<Username>>
}