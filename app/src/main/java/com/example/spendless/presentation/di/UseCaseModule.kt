package com.example.spendless.presentation.di

import com.example.spendless.domain.repository.LocalRepository
import com.example.spendless.domain.repository.SharedPreferencesRepository
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.domain.usecase.local.LocalUseCaseImpl
import com.example.spendless.domain.usecase.sharedPreferences.SharedPreferencesUseCase
import com.example.spendless.domain.usecase.sharedPreferences.SharedPreferencesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLocalUseCase(localRepository: LocalRepository): LocalUseCase{
        return LocalUseCaseImpl(localRepository = localRepository)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesUseCase(sharedPreferencesRepository: SharedPreferencesRepository): SharedPreferencesUseCase{
        return SharedPreferencesUseCaseImpl(sharedPreferencesRepository = sharedPreferencesRepository)
    }
}