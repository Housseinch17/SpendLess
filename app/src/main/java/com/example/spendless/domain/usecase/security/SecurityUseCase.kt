package com.example.spendless.domain.usecase.security

interface SecurityUseCase {
    suspend fun encrypt(bytes: ByteArray): ByteArray
    suspend fun decrypt(bytes: ByteArray): ByteArray?
}