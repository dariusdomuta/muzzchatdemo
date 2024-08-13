package com.example.muzzchatdemo.ui.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBox(modifier: Modifier, onSendChatClickListener: (String) -> Unit) {
    var chatBoxValue by remember { mutableStateOf(TextFieldValue("")) }

    Row(modifier = modifier.padding(top = 16.dp, bottom = 16.dp)) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .background(Color.White, shape = RoundedCornerShape(48.dp))
                .border(
                    2.dp,
                    if (chatBoxValue.text.isEmpty()) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    shape = RoundedCornerShape(48.dp)
                ),
            value = chatBoxValue,
            onValueChange = { newText ->
                chatBoxValue = newText
            },
            placeholder = {
                Text(text = "Type something...")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                containerColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = {
                onSendChatClickListener(chatBoxValue.text)
                chatBoxValue = TextFieldValue("")
            },
            modifier = Modifier
                .alpha(if (chatBoxValue.text.isEmpty()) 0.4f else 1f)
                .clip(
                    CircleShape
                )
                .background(MaterialTheme.colorScheme.primary),
            enabled = !chatBoxValue.text.isEmpty()
        ) {
            Icon(
                imageVector = Icons.Sharp.Send,
                contentDescription = "Send Button",
                tint = Color.White,
            )
        }
    }
}
