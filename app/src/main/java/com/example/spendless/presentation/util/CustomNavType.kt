package com.example.spendless.presentation.util

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.spendless.data.model.Username
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val username = object : NavType<Username>(
        isNullableAllowed = false,
    ) {
        override fun get(bundle: Bundle, key: String): Username? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Username {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Username): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Username) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}