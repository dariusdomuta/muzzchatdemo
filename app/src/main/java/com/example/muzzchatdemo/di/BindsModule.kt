package com.example.muzzchatdemo.di

import com.example.muzzchatdemo.data.repository.ChatRepository
import com.example.muzzchatdemo.data.repository.ChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindsChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository
}
