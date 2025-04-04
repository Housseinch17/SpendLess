package com.example.spendless.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
import com.example.spendless.data.model.Username
import kotlinx.coroutines.flow.Flow

@Dao
interface UsernameDAO {

    //save username
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsername(username: Username)

    //check if username already exists
    @Query("SELECT EXISTS(SELECT 1 FROM username_table WHERE username = :enteredUsername)")
    suspend fun isUsernameExists(enteredUsername: String): Boolean

    //check if user entered is valid(same username and pin)
    @Query("SELECT EXISTS(SELECT 1 FROM username_table WHERE username = :enteredUsername AND pin = :enteredPin)")
    suspend fun isValidUser(enteredUsername: String, enteredPin: String): Boolean

    //get pin of entered username
    @Query("SELECT pin FROM username_table WHERE username = :enteredUsername LIMIT 1")
    suspend fun getUserStoredPin(enteredUsername: String): String?

    //get currentTime of entered username
    @Query("SELECT currentTime FROM username_table WHERE username = :enteredUsername LIMIT 1")
    suspend fun getUserCurrentTime(enteredUsername: String): Long

    //get preferencesFormat of entered username
    @Query("SELECT preferencesFormat FROM username_table WHERE username = :enteredUsername LIMIT 1")
    fun getUserPreferencesFormat(enteredUsername: String): Flow<PreferencesFormat>

    //get sessionExpiryDuration of entered username
    @Query("SELECT sessionExpiryDuration FROM username_table WHERE username = :enteredUsername LIMIT 1")
    suspend fun getUserSessionExpiryDuration(enteredUsername: String): SessionExpiryDuration

    //get lockedOutDuration of entered username
    @Query("SELECT lockedOutDuration FROM username_table WHERE username = :enteredUsername LIMIT 1")
    suspend fun getUserLockedOutDuration(enteredUsername: String): LockedOutDuration

    // Update currentTime
    @Query("UPDATE username_table SET currentTime = :currentTime WHERE username = :enteredUsername")
    suspend fun updateCurrentTime(enteredUsername: String, currentTime: Long)

    // Update preferencesFormat
    @Query("UPDATE username_table SET preferencesFormat = :preferencesFormat WHERE username = :enteredUsername")
    suspend fun updatePreferencesFormat(enteredUsername: String, preferencesFormat: PreferencesFormat)

    // Update sessionExpiryDuration
    @Query("UPDATE username_table SET sessionExpiryDuration = :sessionExpiryDuration WHERE username = :enteredUsername")
    suspend fun updateSessionExpiryDuration(enteredUsername: String, sessionExpiryDuration: SessionExpiryDuration)

    // Update lockedOutDuration
    @Query("UPDATE username_table SET lockedOutDuration = :lockedOutDuration WHERE username = :enteredUsername")
    suspend fun updateLockedOutDuration(enteredUsername: String, lockedOutDuration: LockedOutDuration)

}
