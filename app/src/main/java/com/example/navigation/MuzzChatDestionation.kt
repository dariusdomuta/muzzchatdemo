package com.example.navigation

interface MuzzChatDestination {
    val route: String
}
object MuzzChatDefaultDestination: MuzzChatDestination {
    override val route: String = "muzzChatDefaultDestination"
}
