package com.example.spendless.presentation.di

import com.example.spendless.domain.repository.LocalRepository
import com.example.spendless.domain.usecase.LocalUseCase
import com.example.spendless.domain.usecase.LocalUseCaseImpl
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
        return LocalUseCaseImpl(localRepository)
    }
}