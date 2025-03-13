package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.local.LocalDataSource
import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
): LocalRepository {
    override suspend fun saveUsername(username: Username) {
        localDataSource.saveUsername(username)
    }

    override suspend fun getAllUsername(): Flow<List<Username>> {
        return localDataSource.getAllUsername()
    }
}