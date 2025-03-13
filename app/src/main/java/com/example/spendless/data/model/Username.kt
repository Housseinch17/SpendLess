package com.example.spendless.data.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "username_table")
@Serializable
@Immutable
data class Username(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String = "",
    val pin: Int = 0,
)
