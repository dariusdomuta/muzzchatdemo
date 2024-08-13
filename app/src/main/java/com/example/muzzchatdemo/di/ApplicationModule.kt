package com.example.muzzchatdemo.di

import android.content.Context
import androidx.room.Room
import com.example.muzzchatdemo.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "muzz-database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideChatMessageDao(database: AppDatabase) = database.chatMessagesDao()
}
