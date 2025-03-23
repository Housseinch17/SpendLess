package com.example.spendless.data.datasource.security

interface SecurityDataSource {
    suspend fun encrypt(bytes: ByteArray): ByteArray
    suspend fun decrypt(bytes: ByteArray): ByteArray?
}