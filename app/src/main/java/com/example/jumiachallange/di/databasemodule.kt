package com.example.jumiachallange.di

import android.content.Context
import androidx.room.Room
import com.example.jumiachallange.data.local.JumiaDatabase
import com.example.jumiachallange.util.Constants.JUMIA_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): JumiaDatabase {
        return Room.databaseBuilder(
            context,
            JumiaDatabase::class.java,
            JUMIA_DATABASE
        ).build()
    }

}