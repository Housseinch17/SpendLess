package com.example.spendless.data.datasource

import com.example.spendless.data.datasource.sharedPreferences.SharedPreferencesDataSource
import com.example.spendless.domain.repository.SharedPreferencesRepository
import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
): SharedPreferencesRepository {
    override suspend fun savePreferences(preferencesFormat: PreferencesFormat) {
        sharedPreferencesDataSource.savePreferences(preferencesFormat = preferencesFormat)
    }

    override suspend fun getPreferences(): PreferencesFormat {
        return sharedPreferencesDataSource.getPreferences()
    }
}