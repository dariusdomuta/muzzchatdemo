package com.example.muzzchatdemo.data.repository

import com.example.muzzchatdemo.data.model.ChatMessage
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface ChatRepository {
    fun getChatItems(): Flow<List<ChatMessage>>

    suspend fun sendChatMessage(message: String)
}

class ChatRepositoryImpl @Inject constructor(
    val userRepository: UserRepository
): ChatRepository {

    var _messagesList: MutableStateFlow<List<ChatMessage>> = MutableStateFlow(listOf())
    var messagesList: StateFlow<List<ChatMessage>> = _messagesList.asStateFlow()

    override fun getChatItems(): Flow<List<ChatMessage>> = messagesList

    override suspend fun sendChatMessage(message: String) {
        // Simulate sending a message, which could potentially throw an exception
        if (message.isEmpty()) {
            throw IllegalArgumentException("Message cannot be empty")
        }

        // Simulate network delay
        delay(200)

        // If the message is "error", throw an error to simulate a failure
        if (message == "error") {
            throw Exception("Failed to send message")
        }

        val currentMessagesList = _messagesList.value

        coroutineScope {
            userRepository.getCurrentUser().collect { currentUser ->

                val chatMessageId = if (currentMessagesList.isEmpty()) 0 else (currentMessagesList.last().id + 1)
                val updatedMessages = _messagesList.value.toMutableList()
                updatedMessages.add(ChatMessage(chatMessageId, currentUser.id, System.currentTimeMillis(),  message))
                updatedMessages.add(ChatMessage(chatMessageId + 1, 2, System.currentTimeMillis(),  "Test Reply Message"))
                _messagesList.value = updatedMessages
            }
        }
    }
}
