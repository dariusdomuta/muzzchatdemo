package com.example.muzzchatdemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.muzzchatdemo.data.model.ChatMessage
import com.example.muzzchatdemo.data.usecase.ChatMessageWithUserInfo
import com.example.muzzchatdemo.features.chat.ChatScreen
import com.example.muzzchatdemo.features.chat.ChatViewModel
import com.example.muzzchatdemo.ui.theme.MuzzChatDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuzzChatDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
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

                    ChatScreen(modifier = Modifier
                        .fillMaxSize(), chatMessages) { message ->
                        viewModel.sendMessage(message)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MuzzChatDemoTheme {
        ChatScreen(Modifier, listOf(
            ChatMessageWithUserInfo(ChatMessage(1, 1, 1, "Preview Message"), true))) {
        }
    }
}
