package com.example.muzzchatdemo.data.repository

import com.example.muzzchatdemo.data.model.ChatMessage
import com.example.muzzchatdemo.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UserRepository {
    fun getUsers(): Flow<List<User>>

    fun getCurrentUser(): Flow<User>
}

class UserRepositoryImpl @Inject constructor(): UserRepository {

    val usersList = listOf(
        User(id = 1, name = "Sarah"),
        User(id = 2, name = "Josh"),
        User(id = 3, name = "Tim"),
        User(id = 4, name = "Bob"),
        User(id = 5, name = "Megan")
    )

    override fun getUsers(): Flow<List<User>> =
        flow {
            emit(usersList)
        }

    override fun getCurrentUser(): Flow<User> =
        flow {
            emit(usersList[0])
        }
}
