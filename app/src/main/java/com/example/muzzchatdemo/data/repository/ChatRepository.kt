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
        ChatMessage(1, 1, 0L, "Message 1"),
        ChatMessage(2, 2, 0L, "Message 2"),
        ChatMessage(3, 2, 0L, "Message 3"),
        ChatMessage(4, 1, 0L, "Message 4"),
        ChatMessage(5, 1, 0L, "Message 5"),
        ChatMessage(6, 2, 0L, "Message 6"),
        ChatMessage(7, 2, 0L, "Message 7"),
        ChatMessage(8, 1, 0L, "Message 8"),
        ChatMessage(9, 1, 0L, "Message 9"),
        ChatMessage(10, 2, 0L, "Message 10"),
        ChatMessage(11, 1, 0L, "Message 11"),
        ChatMessage(12, 2, 0L, "Message 12"),
        ChatMessage(13, 2, 0L, "Message 13"),
        ChatMessage(14, 1, 0L, "Message 14"),
        ChatMessage(15, 1, 0L, "Message 15"),
        ChatMessage(16, 2, 0L, "Message 16"),
        ChatMessage(17, 1, 0L, "Message 17"),
        ChatMessage(18, 2, 0L, "Message 18"),
        ChatMessage(19, 2, 0L, "Message 19"),
        ChatMessage(20, 1, 0L, "Message 20"),
        ChatMessage(21, 1, 0L, "Message 21"),
        ChatMessage(22, 2, 0L, "Message 22"),
        ChatMessage(23, 2, 0L, "Message 23"),
        ChatMessage(24, 1, 0L, "Message 24"),
        ChatMessage(25, 1, 0L, "Message 25"),
        ChatMessage(26, 2, 0L, "Message 26"),
        ChatMessage(27, 2, 0L, "Message 27"),
        ChatMessage(28, 1, 0L, "Message 28"),
        ChatMessage(29, 1, 0L, "Message 29"),
        ChatMessage(30, 2, 0L, "Message 30")
    )

    override fun getChatItems(): Flow<List<ChatMessage>> =
        flow {
            emit(messagesList)
        }
}
