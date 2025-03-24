package com.example.spendless.data.model.converters

import androidx.room.TypeConverter
import com.example.spendless.data.model.Currency
import com.example.spendless.data.model.LockedOutDuration
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.data.model.SessionExpiryDuration
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

    @TypeConverter
    fun fromSessionExpiryDuration(value: SessionExpiryDuration): String{
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toSessionExpiryDuration(value: String): SessionExpiryDuration {
        return json.decodeFromString(value) // Deserialize back
    }

    @TypeConverter
    fun fromLockedOutDuration(value: LockedOutDuration): String{
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toLockedOutDuration(value: String): LockedOutDuration {
        return json.decodeFromString(value) // Deserialize back
    }

    @TypeConverter
    fun fromCurrency(value: Currency): String{
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toCurrency(value: String): Currency {
        return json.decodeFromString(value) // Deserialize back
    }

}