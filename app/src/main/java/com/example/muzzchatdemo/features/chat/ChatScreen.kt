package com.example.muzzchatdemo.features.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.ui.commonui.ChatBox
import com.example.muzzchatdemo.ui.commonui.ChatBubble

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatMessages: List<ChatMessageWithUserInfo>,
    sendMessage: (String) -> Unit
) {

    ConstraintLayout(modifier = modifier.fillMaxSize().imePadding()) {
        val (messages, chatBox) = createRefs()

        val listState = rememberLazyListState()
        LaunchedEffect(key1 = chatMessages.size) {
            listState.animateScrollToItem(chatMessages.size)
        }
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
                },
            state = listState
        ) {
            items(chatMessages, key = { it.chatMessage.id }) { chatMessage ->
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
            sendMessage(message)
        }
    }
}
