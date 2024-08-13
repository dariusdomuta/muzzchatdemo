package com.example.muzzchatdemo.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.data.usecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
): ViewModel() {

    companion object {
        private const val TIMESTAMP_THRESHOLD_FOR_SPACING = 20_000L
        private const val TIMESTAMP_THRESHOLD_FOR_SECTIONING = 3600000L
        private const val CHAT_TIME_FORMAT = "EEEE hh:mm a"
    }

    private val _chatMessagesFlow = MutableStateFlow<List<DisplayableChatModel>>(emptyList())
    val chatMessagesFlow = _chatMessagesFlow.asStateFlow()

    //TODO: Move this error messages flow into a base view model. Also add a base for the loading state. Another Idea could be having a global state handler
    // that all the screens subscribe to in order to catch exceptions, parse them and display them properly via a snackbar or something nicer.
    private val _errorMessagesFlow = MutableStateFlow<Exception?>(null)
    val errorMessagesFlow = _errorMessagesFlow.asStateFlow()

    fun getChatMessages() {
        viewModelScope.launch {
            chatUseCase.getChatMessages().collect { items ->
                if (items.isEmpty() || items.size == 1) {
                    _chatMessagesFlow.value = items.map { DisplayableChatItemWithLargeSpacing(it) }
                }

                val chatItems = mutableListOf<DisplayableChatModel>()

                items.forEachIndexed() { index, item ->
                    mapChatItemToDisplayableItem(index, chatItems, item, items)
                    _chatMessagesFlow.value = chatItems
                }
            }
        }
    }

    private fun mapChatItemToDisplayableItem(
        index: Int,
        chatItems: MutableList<DisplayableChatModel>,
        item: ChatMessageWithUserInfo,
        items: List<ChatMessageWithUserInfo>
    ) {
        if (index == 0) {
            chatItems.add(DisplayableChatItemWithLargeSpacing(item))
        } else {
            val previousChat = items[index - 1]
            val currentChat = item

            val previousChatTimestamp = previousChat.chatMessage.timestamp
            val currentChatTimestamp = currentChat.chatMessage.timestamp

            val timeDifferenceInMs = (currentChatTimestamp - previousChatTimestamp)

            val isWithin20Seconds = timeDifferenceInMs < TIMESTAMP_THRESHOLD_FOR_SPACING
            val isWithinAnHour = timeDifferenceInMs < TIMESTAMP_THRESHOLD_FOR_SECTIONING

            val isPreviousMessageFromAnotherUser = previousChat.chatMessage.authorId != currentChat.chatMessage.authorId

            if (!isWithinAnHour) {
                chatItems.add(
                    TimestampChatItem(
                        formatTimestampToDisplayableTime(
                            currentChatTimestamp
                        )
                    )
                )
                chatItems.add(DisplayableChatItemWithLargeSpacing(item))
            } else if (!isWithin20Seconds) {
                chatItems.add(DisplayableChatItemWithLargeSpacing(item))
            } else {
                if (isPreviousMessageFromAnotherUser) {
                    chatItems.add(DisplayableChatItemWithLargeSpacing(item))
                } else {
                    chatItems.add(DisplayableChatItemWithSmallSpacing(item))
                }
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                chatUseCase.sendChatMessage(message)
            } catch (e: Exception) {
                _errorMessagesFlow.value = e
            }
        }
    }

    //TODO: MOVE THIS INTO A TIME FORMATTER UTIL CLASS
    fun formatTimestampToDisplayableTime(timestamp: Long): String {
        // Create a SimpleDateFormat instance with the desired pattern
        val dateFormat = SimpleDateFormat(CHAT_TIME_FORMAT, Locale.getDefault())

        // Convert the timestamp to a Date object
        val date = Date(timestamp)

        // Format the date into a string
        return dateFormat.format(date)
    }
}
