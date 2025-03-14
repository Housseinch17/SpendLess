package com.example.spendless.data.datasource.local

import android.util.Log
import com.example.spendless.data.model.Username
import com.example.spendless.data.roomdb.UsernameDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalDataSourceImpl @Inject constructor(
    @Named("DispatchersIO")
    private val coroutineDispatcher: CoroutineDispatcher,
    private val usernameDAO: UsernameDAO,
): LocalDataSource {
    override suspend fun saveUsername(username: Username): Unit = withContext(coroutineDispatcher){
        try {
            usernameDAO.saveUsername(username)
            Log.d("MyTag","saveUsername successfully completed")
        }catch (e: Exception){
            Log.d("MyTag","saveUsername failed  ${e.message}")
        }
    }

    override suspend fun isUsernameExists(enteredUsername: String): Boolean = withContext(coroutineDispatcher){
        return@withContext usernameDAO.isUsernameExists(enteredUsername = enteredUsername)
    }

    override suspend fun isValidUser(enteredUsername: String, enteredPin: Int): Boolean = withContext(coroutineDispatcher){
        return@withContext usernameDAO.isValidUser(enteredUsername = enteredUsername, enteredPin = enteredPin)
    }
}