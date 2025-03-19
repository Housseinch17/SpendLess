package com.example.spendless.domain.repository

import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat

interface SharedPreferencesRepository {
    suspend fun savePreferences(preferencesFormat: PreferencesFormat)
    suspend fun getPreferences(): PreferencesFormat
}