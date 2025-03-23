package com.example.spendless.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spendless.data.model.Username
import com.example.spendless.data.model.converters.Converters

@Database(
    entities = [Username::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UsernameDataBase: RoomDatabase() {
    abstract fun usernameDAO(): UsernameDAO
}