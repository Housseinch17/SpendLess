package com.example.spendless.presentation.di

import com.example.spendless.domain.repository.CurrentTimeRepository
import com.example.spendless.domain.repository.LocalRepository
import com.example.spendless.domain.repository.SecurityRepository
import com.example.spendless.domain.usecase.currentTime.CurrentTimeUseCase
import com.example.spendless.domain.usecase.currentTime.CurrentTimeUseCaseImpl
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.domain.usecase.local.LocalUseCaseImpl
import com.example.spendless.domain.usecase.security.SecurityUseCase
import com.example.spendless.domain.usecase.security.SecurityUseCaseImpl
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
    fun provideLocalUseCase(localRepository: LocalRepository): LocalUseCase {
        return LocalUseCaseImpl(localRepository = localRepository)
    }

    @Provides
    @Singleton
    fun provideSecurityUseCase(securityRepository: SecurityRepository): SecurityUseCase {
        return SecurityUseCaseImpl(securityRepository = securityRepository)
    }

    @Provides
    @Singleton
    fun provideCurrentTimeUseCase(currentTimeRepository: CurrentTimeRepository): CurrentTimeUseCase {
        return CurrentTimeUseCaseImpl(currentTimeRepository = currentTimeRepository)
    }

}