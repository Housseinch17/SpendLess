package com.example.spendless.data.model.converters

import androidx.room.TypeConverter
import com.example.spendless.data.model.PreferencesFormat
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class Converters {
    private val json = Json { encodeDefaults = true } // Ensures all default values are saved

    @TypeConverter
    fun fromPreferencesFormat(value: PreferencesFormat): String {
        return json.encodeToString(value) // Serialize to JSON
    }

    @TypeConverter
    fun toPreferencesFormat(value: String): PreferencesFormat {
        return json.decodeFromString(value) // Deserialize back
    }
}