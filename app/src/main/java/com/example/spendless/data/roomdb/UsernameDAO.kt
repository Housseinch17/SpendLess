package com.example.spendless.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spendless.data.model.Username

@Dao
interface UsernameDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsername(username: Username)

    @Query("SELECT EXISTS(SELECT 1 FROM username_table WHERE username = :enteredUsername)")
    suspend fun isUsernameExists(enteredUsername: String): Boolean


    @Query("SELECT EXISTS(SELECT 1 FROM username_table WHERE username = :enteredUsername AND pin = :enteredPin)")
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean

}