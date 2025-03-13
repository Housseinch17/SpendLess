package com.example.spendless.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

@Dao
interface UsernameDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsername(username: Username)

    //can use flow for live updates but prefer to implement refresh
    @Query("SELECT * FROM username_table")
    fun getAllUsername(): Flow<List<Username>>
}