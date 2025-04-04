package com.example.spendless.data.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.spendless.data.model.converters.Converters
import kotlinx.serialization.Serializable

@Entity(tableName = "username_table")
@Serializable
@Immutable
data class Username(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String = "",
    val pin: String = "",
    val currentTime: Long = 0,
    @TypeConverters(Converters::class)
    val preferencesFormat: PreferencesFormat = PreferencesFormat(),
    val sessionExpiryDuration: SessionExpiryDuration = SessionExpiryDuration(),
    val lockedOutDuration: LockedOutDuration = LockedOutDuration()
)
