package com.example.spendless.presentation.di

import com.example.spendless.data.datasource.TimeRepositoryImpl
import com.example.spendless.data.datasource.LocalRepositoryImpl
import com.example.spendless.data.datasource.SecurityRepositoryImpl
import com.example.spendless.data.datasource.currentTime.TimeDataSource
import com.example.spendless.data.datasource.currentTime.TimeDataSourceImpl
import com.example.spendless.data.datasource.local.LocalDataSource
import com.example.spendless.data.datasource.local.LocalDataSourceImpl
import com.example.spendless.data.datasource.security.SecurityDataSource
import com.example.spendless.data.datasource.security.SecurityDataSourceImpl
import com.example.spendless.data.roomdb.UsernameDAO
import com.example.spendless.domain.repository.TimeRepository
import com.example.spendless.domain.repository.LocalRepository
import com.example.spendless.domain.repository.SecurityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {


    @Provides
    @Singleton
    fun provideLocalDataSource(
        @Named("DispatchersIO") coroutineDispatcher: CoroutineDispatcher,
        usernameDAO: UsernameDAO
    ): LocalDataSource {
        return LocalDataSourceImpl(coroutineDispatcher, usernameDAO)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(localDataSource: LocalDataSource): LocalRepository {
        return LocalRepositoryImpl(localDataSource)
    }


    @Provides
    @Singleton
    fun provideSecurityDataSource(
        @Named("DispatchersIO") coroutineDispatcher: CoroutineDispatcher,
    ): SecurityDataSource {
        return SecurityDataSourceImpl(coroutineDispatcher = coroutineDispatcher)
    }


    @Provides
    @Singleton
    fun provideSecurityRepository(securityDataSource: SecurityDataSource): SecurityRepository {
        return SecurityRepositoryImpl(securityDataSource = securityDataSource)
    }

    @Provides
    @Singleton
    fun provideCurrentTimeDataSource(): TimeDataSource{
        return TimeDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideCurrentTimeRepository(timeDataSource: TimeDataSource): TimeRepository{
        return TimeRepositoryImpl(timeDataSource = timeDataSource)
    }
}