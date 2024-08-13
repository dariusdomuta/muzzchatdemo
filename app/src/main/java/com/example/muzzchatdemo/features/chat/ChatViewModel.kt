package com.example.muzzchatdemo.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.data.usecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
): ViewModel() {

    private val _chatMessagesFlow = MutableStateFlow<List<ChatMessageWithUserInfo>>(emptyList())
    val chatMessagesFlow = _chatMessagesFlow.asStateFlow()

    fun getChatMessages() {
        viewModelScope.launch {
            chatUseCase.getChatMessages().collect { items ->
                _chatMessagesFlow.value = items
            }
        }
    }

    fun sendMessage(message: String) {

    }
}
