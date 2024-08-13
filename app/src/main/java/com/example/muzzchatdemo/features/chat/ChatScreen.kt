package com.example.muzzchatdemo.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muzzchatdemo.ui.commonui.ChatBox
import com.example.muzzchatdemo.ui.commonui.ChatBubble

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val chatMessages by viewModel.chatMessagesFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getChatMessages()
    }

    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (messages, chatBox) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(messages) {
                    top.linkTo(parent.top)
                    bottom.linkTo(chatBox.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height =
                        Dimension.fillToConstraints // Make sure LazyColumn fills available space
                }
        ) {
            items(chatMessages) { chatMessage ->
                ChatBubble(chatMessage)
            }
        }

        ChatBox(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(chatBox) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) { message ->
            viewModel.sendMessage(message)
        }
    }
}
