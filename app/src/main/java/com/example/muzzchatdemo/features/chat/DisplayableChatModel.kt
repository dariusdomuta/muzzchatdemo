package com.example.muzzchatdemo.features.chat

import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo

open class DisplayableChatModel(val id: Int)

data class DisplayableChatItemWithSmallSpacing(
    val chatMessageWithUserInfo: ChatMessageWithUserInfo
) : DisplayableChatModel(chatMessageWithUserInfo.chatMessage.id)

data class DisplayableChatItemWithLargeSpacing(
    val chatMessageWithUserInfo: ChatMessageWithUserInfo
) : DisplayableChatModel(chatMessageWithUserInfo.chatMessage.id)

data class TimestampChatItem(
    val displayableTimestamp: String
) : DisplayableChatModel(displayableTimestamp.hashCode())
