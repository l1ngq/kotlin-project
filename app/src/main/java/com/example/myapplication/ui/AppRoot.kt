package com.example.myapplication.ui

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.showReminder
import com.example.myapplication.ui.screens.AddEditSubscriptionScreen
import com.example.myapplication.ui.screens.MainScreen
import com.example.myapplication.ui.screens.SubscriptionDetailScreen

@Composable
fun AppRoot() {
    val context = LocalContext.current
    val viewModel: SubscriptionViewModel = viewModel()

    val subscriptions by viewModel.subscriptions.collectAsState(initial = emptyList())

    var currentScreen by rememberSaveable { mutableStateOf("main") }
    var selectedSubscriptionId by rememberSaveable { mutableStateOf<Int?>(null) }
    var editingId by rememberSaveable { mutableStateOf<Int?>(null) }

    val subscriptionToEdit = subscriptions.find { it.id == editingId }

    BackHandler(enabled = currentScreen != "main") {
        currentScreen = when (currentScreen) {
            "add_edit" -> if (subscriptionToEdit != null) "detail" else "main"
            else -> "main"
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    LaunchedEffect(subscriptions) {
        showReminder(context, subscriptions)
    }

    when (currentScreen) {
        "main" -> {
            MainScreen(
                subscriptions = subscriptions,
                onAddClick = {
                    editingId = null
                    currentScreen = "add_edit"
                },
                onSubscriptionClick = { id ->
                    selectedSubscriptionId = id
                    currentScreen = "detail"
                }
            )
        }

        "add_edit" -> {
            AddEditSubscriptionScreen(
                subscriptionToEdit = subscriptionToEdit,
                onSave = { subscription ->
                    viewModel.save(subscription)
                    currentScreen = "main"
                },
                onBack = {
                    currentScreen = if (subscriptionToEdit != null) "detail" else "main"
                }
            )
        }

        "detail" -> {
            val selectedSub = subscriptions.find { it.id == selectedSubscriptionId }
            if (selectedSub != null) {
                SubscriptionDetailScreen(
                    subscription = selectedSub,
                    onBack = {
                        currentScreen = "main"
                    },
                    onEdit = { sub ->
                        editingId = sub.id
                        currentScreen = "add_edit"
                    },
                    onDelete = { id ->
                        currentScreen = "main"
                        viewModel.delete(id)
                    },
                    onToggleActive = { sub ->
                        viewModel.toggleActive(sub)
                    },
                    onKeepSubscription = { sub ->
                        viewModel.keepAsPaid(sub)
                    }
                )
            }
        }
    }
}
