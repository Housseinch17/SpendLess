package com.example.spendless.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spendless.data.model.Username

@Database(
    entities = [Username::class],
    version = 1,
    exportSchema = false
)
abstract class UsernameDataBase: RoomDatabase() {
    abstract fun usernameDAO(): UsernameDAO
}