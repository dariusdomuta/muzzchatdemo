package com.example.muzzchatdemo.ui.commonui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun ChatBox(modifier: Modifier, onSendChatClickListener: (String) -> Unit) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.weight(1f),
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            placeholder = {
                Text(text = "Type something...")
            }
        )
        IconButton(
            onClick = {
                onSendChatClickListener(chatBoxValue.text)
            }
        ) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send Button")
        }
    }
}
