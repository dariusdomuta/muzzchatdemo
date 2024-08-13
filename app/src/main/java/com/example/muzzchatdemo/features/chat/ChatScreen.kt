package com.example.muzzchatdemo.features.chat

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muzzchatdemo.R
import com.example.muzzchatdemo.ui.commonui.ChatBox
import com.example.muzzchatdemo.ui.commonui.ChatBubble
import com.example.muzzchatdemo.ui.theme.MoreButtonTint
import com.example.muzzchatdemo.ui.theme.MuzzChatDemoTheme

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

    Column {
        ChatHeader("Darius", onBackClick = {}, onMenuClick = {})
        ChatView(modifier, chatMessages, viewModel)
    }
}

@Composable
private fun ChatView(
    modifier: Modifier,
    chatMessages: List<DisplayableChatModel>,
    viewModel: ChatViewModel
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 16.dp)
    ) {

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
            itemsIndexed(items = chatMessages, key = { index, item -> item.id }) { index, displayableItem ->
                when (displayableItem) {
                    is DisplayableChatItemWithSmallSpacing -> {
                        Spacer(modifier = Modifier.height(2.dp))
                        ChatBubble(displayableItem.chatMessageWithUserInfo)
                    }
                    is DisplayableChatItemWithLargeSpacing -> {
                        Spacer(modifier = Modifier.height(16.dp))
                        ChatBubble(displayableItem.chatMessageWithUserInfo)
                    }
                    is TimestampChatItem -> {
                        BoldFirstWordText(displayableItem.displayableTimestamp)
                    }
                }
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

@Composable
fun ChatHeader(
    userName: String,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                8.dp,
                ambientColor = Color.Black,
                spotColor = Color.Black,
                clip = false
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier
                .padding(end = 8.dp)
                .size(60.dp)) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(60.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.profile_pic),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = userName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onMenuClick) {
                Icon(painter = painterResource(id = R.drawable.baseline_more_horiz_24), contentDescription = "Menu", modifier = Modifier.size(40.dp), tint = MoreButtonTint)
            }

            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Composable
fun BoldFirstWordText(displayableTimestamp: String) {
    // Build the annotated string with bold style for the first word
    val annotatedString = buildAnnotatedString {
        val firstSpaceIndex = displayableTimestamp.indexOf(" ")

        // Determine the end index for the first word
        val endIndex = if (firstSpaceIndex == -1) displayableTimestamp.length else firstSpaceIndex

        // Apply bold style to the first word
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(displayableTimestamp.substring(0, endIndex))
        }

        // Append the rest of the string without any special styling
        append(displayableTimestamp.substring(endIndex))
    }

    // Display the annotated string in a Text composable
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = annotatedString,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MuzzChatDemoTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChatScreen(Modifier)
        }
    }
}