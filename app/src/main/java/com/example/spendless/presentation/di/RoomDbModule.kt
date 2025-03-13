package com.example.spendless.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.spendless.data.roomdb.UsernameDAO
import com.example.spendless.data.roomdb.UsernameDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {

    @Singleton
    @Provides
    fun provideUsernameDataBase(app: Application): UsernameDataBase{
        return Room.databaseBuilder(app,UsernameDataBase::class.java,"SpendLessApp")
            .build()
    }

    @Singleton
    @Provides
    fun provideUsernameDAO(usernameDataBase: UsernameDataBase): UsernameDAO{
        return usernameDataBase.usernameDAO()
    }

}