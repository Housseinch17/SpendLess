package com.example.spendless.domain.usecase.sharedPreferences

import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat

interface SharedPreferencesUseCase {
    suspend fun savePreferences(preferencesFormat: PreferencesFormat)
    suspend fun getPreferences(): PreferencesFormat
}