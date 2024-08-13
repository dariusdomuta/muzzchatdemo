package com.example.muzzchatdemo.ui.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.muzzchatdemo.R
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.ui.theme.BlueGray
import com.example.muzzchatdemo.ui.theme.MuzzYellow

@Composable
fun ChatBubble(chatMessageWithUserInfo: ChatMessageWithUserInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (chatMessageWithUserInfo.isCurrentUserTheSender) 64.dp else 0.dp,
                end = if (chatMessageWithUserInfo.isCurrentUserTheSender) 0.dp else 64.dp
            ),
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
                .background(if (chatMessageWithUserInfo.isCurrentUserTheSender) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = chatMessageWithUserInfo.chatMessage.message, color =
                if (chatMessageWithUserInfo.isCurrentUserTheSender) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
            )
            if (chatMessageWithUserInfo.isCurrentUserTheSender) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .padding(4.dp),
                    painter = painterResource(id = R.drawable.baseline_done_all_24),
                    contentDescription = "Send Button",
                    tint = if (chatMessageWithUserInfo.isItemSeen) MuzzYellow else BlueGray
                )
            }
        }
    }
}
