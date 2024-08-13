package com.example.muzzchatdemo.di

import com.example.muzzchatdemo.data.repository.ChatRepository
import com.example.muzzchatdemo.data.repository.ChatRepositoryImpl
import com.example.muzzchatdemo.data.repository.UserRepository
import com.example.muzzchatdemo.data.repository.UserRepositoryImpl
import com.example.muzzchatdemo.data.usecase.ChatUseCase
import com.example.muzzchatdemo.data.usecase.ChatUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindsChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindsChatUseCase(chatUseCaseImpl: ChatUseCaseImpl): ChatUseCase
}
