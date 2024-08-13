package com.example.muzzchatdemo.data.repository

import com.example.muzzchatdemo.data.database.ChatMessageDao
import com.example.muzzchatdemo.data.model.ChatMessage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random

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

                val chatUsersRandom = Random.nextInt()

                val currentUserId = if (chatUsersRandom % 2 == 0) {
                    currentUser.first.id
                } else {
                    currentUser.second.id
                }
                chatMessagesDao.insertMessages(ChatMessage(authorId = currentUserId, timestamp = System.currentTimeMillis(), message = message))
            }
        }
    }
}
