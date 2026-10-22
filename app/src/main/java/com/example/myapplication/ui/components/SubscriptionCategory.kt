package com.example.myapplication.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class SubscriptionCategory(
    val key: String,
    val icon: ImageVector
)

val subscriptionCategories = listOf(
    SubscriptionCategory("music", Icons.Default.MusicNote),
    SubscriptionCategory("sport", Icons.Default.FitnessCenter),
    SubscriptionCategory("video", Icons.Default.PlayArrow),
    SubscriptionCategory("work", Icons.Default.Settings),
    SubscriptionCategory("star", Icons.Default.Star),
    SubscriptionCategory("call", Icons.Default.Call)
)

fun iconForCategory(category: String?): ImageVector? =
    subscriptionCategories.firstOrNull { it.key == category }?.icon
