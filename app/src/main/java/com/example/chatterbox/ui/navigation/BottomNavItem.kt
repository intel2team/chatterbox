package com.example.chatterbox.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var route: String, var outlinedIcon: ImageVector, var filledIcon: ImageVector, var title: String) {
    object Friends : BottomNavItem("friends", Icons.Outlined.Person, Icons.Filled.Person,"친구")
    object Chat : BottomNavItem("chat", Icons.Outlined.Chat, Icons.Filled.Chat, "채팅방")
    object Shop : BottomNavItem("shop", Icons.Outlined.Shop, Icons.Filled.Shop, "상점")
    object Others : BottomNavItem("others", Icons.Outlined.Settings, Icons.Filled.Settings, "설정")
}
