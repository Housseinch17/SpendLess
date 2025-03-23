package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.security.SecurityDataSource
import com.example.spendless.domain.repository.SecurityRepository
import javax.inject.Inject

class SecurityRepositoryImpl @Inject constructor(
    private val securityDataSource: SecurityDataSource
): SecurityRepository {
    override suspend fun encrypt(bytes: ByteArray): ByteArray {
        return securityDataSource.encrypt(bytes = bytes)
    }

    override suspend fun decrypt(bytes: ByteArray): ByteArray? {
        return securityDataSource.decrypt(bytes = bytes)
    }
}