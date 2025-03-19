package com.example.spendless.domain.usecase.sharedPreferences

import com.example.spendless.domain.repository.SharedPreferencesRepository
import com.example.spendless.presentation.screens.onboardingPreferencesPage.PreferencesFormat
import javax.inject.Inject

class SharedPreferencesUseCaseImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
): SharedPreferencesUseCase{
    override suspend fun savePreferences(preferencesFormat: PreferencesFormat) {
        sharedPreferencesRepository.savePreferences(preferencesFormat = preferencesFormat)
    }

    override suspend fun getPreferences(): PreferencesFormat {
        return sharedPreferencesRepository.getPreferences()
    }
}