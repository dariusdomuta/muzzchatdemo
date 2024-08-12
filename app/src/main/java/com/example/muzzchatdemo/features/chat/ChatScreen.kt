package com.example.muzzchatdemo.features.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val chatMessages by viewModel.chatMessagesFlow.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getChatMessages()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        chatMessages.forEach {
            Text(text = it.message)
        }
    }
}
