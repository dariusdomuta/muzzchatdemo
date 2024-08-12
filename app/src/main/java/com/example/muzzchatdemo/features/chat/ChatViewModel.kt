package com.example.muzzchatdemo.features.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzzchatdemo.data.model.ChatMessage
import com.example.muzzchatdemo.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
): ViewModel() {

    private val _chatMessagesFlow = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessagesFlow = _chatMessagesFlow.asStateFlow()

    fun getChatMessages() {
        viewModelScope.launch {
            chatRepository.getChatItems().collect { items ->
                _chatMessagesFlow.value = items
            }
        }
    }
}
