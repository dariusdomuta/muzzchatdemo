package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.muzzchatdemo.features.chat.ChatScreen
import com.example.muzzchatdemo.features.chat.ChatViewModel

@Composable
fun MuzzChatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MuzzChatDefaultDestination.route,
        modifier = modifier
    ) {
        composable(route = MuzzChatDefaultDestination.route) {
            val viewModel: ChatViewModel = hiltViewModel()
            ChatScreen(viewModel = viewModel)
        }
    }
}
