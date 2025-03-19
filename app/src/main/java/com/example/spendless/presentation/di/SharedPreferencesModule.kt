package com.example.spendless.presentation.di

import android.content.Context
import android.content.SharedPreferences
import com.example.spendless.data.datasource.SharedPreferencesRepositoryImpl
import com.example.spendless.data.datasource.sharedPreferences.SharedPreferencesDataSource
import com.example.spendless.data.datasource.sharedPreferences.SharedPreferencesDataSourceImpl
import com.example.spendless.domain.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(sharedPreferencesDataSource: SharedPreferencesDataSource): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(sharedPreferencesDataSource = sharedPreferencesDataSource)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesDataSource(
        sharedPreferences: SharedPreferences,
        @Named("DispatchersIO") coroutineDispatcher: CoroutineDispatcher
    ): SharedPreferencesDataSource {
        return SharedPreferencesDataSourceImpl(
            sharedPreferences = sharedPreferences,
            coroutineDispatcher = coroutineDispatcher
        )
    }
}

