package com.example.spendless.domain.usecase.security

import com.example.spendless.domain.repository.SecurityRepository
import javax.inject.Inject

class SecurityUseCaseImpl @Inject constructor(
    private val securityRepository: SecurityRepository
) : SecurityUseCase {
    override suspend fun encrypt(bytes: ByteArray): ByteArray {
        return securityRepository.encrypt(bytes = bytes)
    }

    override suspend fun decrypt(bytes: ByteArray): ByteArray? {
        return securityRepository.decrypt(bytes = bytes)
    }
}