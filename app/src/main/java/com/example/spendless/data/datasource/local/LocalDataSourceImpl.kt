package com.example.spendless.data.datasource.local

import android.util.Log
import com.example.spendless.data.model.Username
import com.example.spendless.data.roomdb.UsernameDAO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getAllUsername(): Flow<List<Username>> = withContext(coroutineDispatcher){
        usernameDAO.getAllUsername()
    }
}