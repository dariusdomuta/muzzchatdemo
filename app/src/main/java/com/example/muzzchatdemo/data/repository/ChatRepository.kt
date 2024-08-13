package com.example.muzzchatdemo.data.repository

import com.example.muzzchatdemo.data.database.ChatMessageDao
import com.example.muzzchatdemo.data.model.ChatMessage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatRepository {
    fun getChatItems(): Flow<List<ChatMessage>>

    suspend fun sendChatMessage(message: String)
}

class ChatRepositoryImpl @Inject constructor(
    val userRepository: UserRepository,
    val chatMessagesDao: ChatMessageDao
): ChatRepository {

    override fun getChatItems(): Flow<List<ChatMessage>> = chatMessagesDao.getAll()

    override suspend fun sendChatMessage(message: String) {
        // Simulate sending a message, which could potentially throw an exception
        if (message.isEmpty()) {
            throw IllegalArgumentException("Message cannot be empty")
        }

        // Simulate network delay
        delay(100)

        // If the message is "error", throw an error to simulate a failure
        if (message == "error") {
            throw Exception("Failed to send message")
        }

        coroutineScope {
            userRepository.getCurrentlyChattingUsers().collect { currentUser ->

                chatMessagesDao.insertMessages(ChatMessage(authorId = currentUser.first.id, timestamp = System.currentTimeMillis(), message = message))

                // Simulate network delay
                delay(1000)

                chatMessagesDao.insertMessages(ChatMessage(authorId = currentUser.second.id, timestamp = System.currentTimeMillis(), message = "Mocked Reply Message"))
            }
        }
    }
}
