package com.example.spendless.domain.usecase

import com.example.spendless.data.model.Username
import com.example.spendless.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository,
): LocalUseCase {
    override suspend fun saveUsername(username: Username) {
        return localRepository.saveUsername(username)
    }

    override suspend fun getAllUsername(): Flow<List<Username>> {
        return localRepository.getAllUsername()
    }
}