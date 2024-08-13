package com.example.muzzchatdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.muzzchatdemo.features.chat.ChatScreen
import com.example.muzzchatdemo.ui.theme.MuzzChatDemoTheme
import com.example.navigation.MuzzChatNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuzzChatDemoTheme {

                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    MuzzChatNavHost(navController = navController)
                }
            }
        }
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
