package com.example.chatterbox.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var route: String, var icon: ImageVector, var title: String) {
    object Friends : BottomNavItem("friends", Icons.Outlined.Person, "친구")
    object Chat : BottomNavItem("chat", Icons.Outlined.Chat, "채팅방")
    object Shop : BottomNavItem("shop", Icons.Outlined.Shop, "상점")
    object Others : BottomNavItem("others", Icons.Outlined.MoreVert, "설정")
}
