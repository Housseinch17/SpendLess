package com.example.spendless.data.datasource.sharedPreferences

import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat

interface SharedPreferencesDataSource {
    suspend fun savePreferences(preferencesFormat: PreferencesFormat)
    suspend fun getPreferences(): PreferencesFormat
}