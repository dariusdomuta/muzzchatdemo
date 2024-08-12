package com.example.muzzchatdemo.data.repository

import com.example.muzzchatdemo.data.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ChatRepository {
    fun getChatItems(): Flow<List<ChatMessage>>
}

class ChatRepositoryImpl @Inject constructor(): ChatRepository {

    val messagesList = listOf(
        ChatMessage(1, "User 1", 0L, "Message 1"),
        ChatMessage(2, "User 2", 0L, "Message 2"),
        ChatMessage(3, "User 1", 0L, "Message 3"),
        ChatMessage(4, "User 2", 0L, "Message 4"),
    )

    override fun getChatItems(): Flow<List<ChatMessage>> =
        flow {
            emit(messagesList)
        }
}
