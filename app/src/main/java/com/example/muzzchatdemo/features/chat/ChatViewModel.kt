package com.example.muzzchatdemo.features.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.muzzchatdemo.data.repository.ChatRepository
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

    //TODO: Move this error messages flow into a base view model. Also add a base for the loading state. Another Idea could be having a global state handler
    // that all the screens subscribe to in order to catch exceptions, parse them and display them properly via a snackbar or something nicer.
    private val _errorMessagesFlow = MutableStateFlow<Exception?>(null)
    val errorMessagesFlow = _errorMessagesFlow.asStateFlow()

    fun getChatMessages() {
        viewModelScope.launch {
            chatUseCase.getChatMessages().collect { items ->
                _chatMessagesFlow.value = items
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
}
