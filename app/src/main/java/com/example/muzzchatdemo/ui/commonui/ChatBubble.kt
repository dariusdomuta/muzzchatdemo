package com.example.muzzchatdemo.ui.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.ui.theme.Pink40

@Composable
fun ChatBubble(chatMessageWithUserInfo: ChatMessageWithUserInfo) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (chatMessageWithUserInfo.isCurrentUserTheSender) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier =
            Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (chatMessageWithUserInfo.isCurrentUserTheSender) 48f else 0f,
                        bottomEnd = if (chatMessageWithUserInfo.isCurrentUserTheSender) 0f else 48f
                    )
                )
                .background(Pink40)
                .padding(16.dp)
        ) {
            Text(text = chatMessageWithUserInfo.chatMessage.message)
        }
    }
}
