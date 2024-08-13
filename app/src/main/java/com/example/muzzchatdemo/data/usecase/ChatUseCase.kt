package com.example.muzzchatdemo.data.usecase

import com.example.muzzchatdemo.data.model.ChatMessage
import com.example.muzzchatdemo.data.repository.ChatRepository
import com.example.muzzchatdemo.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

interface ChatUseCase {
    fun getChatMessages(): Flow<List<ChatMessageWithUserInfo>>

    suspend fun sendChatMessage(message: String)
}

class ChatUseCaseImpl @Inject constructor(
    val chatRepository: ChatRepository,
    val userRepository: UserRepository
): ChatUseCase {
    override fun getChatMessages(): Flow<List<ChatMessageWithUserInfo>> =
        combine(
            chatRepository.getChatItems(),
            userRepository.getCurrentlyChattingUsers()
        ) { chatItems, currentUser ->

            chatItems.map { chatMessage ->
                ChatMessageWithUserInfo(chatMessage, chatMessage.authorId == currentUser.first.id, chatItems.last() == chatMessage)
            }
        }

    override suspend fun sendChatMessage(message: String) {
        chatRepository.sendChatMessage(message)
    }
}

data class ChatMessageWithUserInfo(
    val chatMessage: ChatMessage,
    val isCurrentUserTheSender: Boolean,
    val isItemSeen: Boolean
)
