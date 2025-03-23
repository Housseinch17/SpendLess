package com.example.spendless.domain.repository

interface SecurityRepository {
    suspend fun encrypt(bytes: ByteArray): ByteArray
    suspend fun decrypt(bytes: ByteArray): ByteArray?
}