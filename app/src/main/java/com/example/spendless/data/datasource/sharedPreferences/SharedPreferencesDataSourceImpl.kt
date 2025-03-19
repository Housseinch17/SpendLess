package com.example.spendless.data.datasource.sharedPreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.coroutineContext


class SharedPreferencesDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @Named("DispatchersIO")
    private val coroutineDispatcher: CoroutineDispatcher,
) : SharedPreferencesDataSource {
    override suspend fun savePreferences(preferencesFormat: PreferencesFormat) =
        withContext(coroutineDispatcher) {
            val jsonString = Json.encodeToString(preferencesFormat)
            sharedPreferences.edit {
                putString("preferences_key", jsonString)
            }
        }

    override suspend fun getPreferences(): PreferencesFormat {
        return withContext(coroutineContext) {
            val jsonString = sharedPreferences.getString("preferences_key", null)
            jsonString?.let { Json.decodeFromString(it) } ?: PreferencesFormat()
        }
    }
}