package com.example.muzzchatdemo.features.chat

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.muzzchatdemo.ui.commonui.ChatBox
import com.example.muzzchatdemo.ui.commonui.ChatBubble
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: ChatViewModel = viewModel()
) {
    val context = LocalContext.current

    // Collect state from ViewModel
    val chatMessages by viewModel.chatMessagesFlow.collectAsStateWithLifecycle()
    val errorToasts by viewModel.errorMessagesFlow.collectAsStateWithLifecycle()

    // Show error toast if necessry
    LaunchedEffect(key1 = errorToasts) {
        errorToasts?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getChatMessages()
    }

    ConstraintLayout(modifier = modifier
        .fillMaxSize()
        .imePadding()) {
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
            viewModel.sendMessage(message)
        }
    }
}
