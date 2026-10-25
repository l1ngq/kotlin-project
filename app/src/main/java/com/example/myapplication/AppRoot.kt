package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.domain.Subscription
import com.example.myapplication.ui.screens.AddEditSubscriptionScreen
import com.example.myapplication.ui.screens.MainScreen
import com.example.myapplication.ui.screens.SubscriptionDetailScreen
import kotlinx.coroutines.launch

@Composable
fun AppRoot() {
    val context = LocalContext.current
    val dao = remember { AppDatabase.getInstance(context).subscriptionDao() }
    val scope = rememberCoroutineScope()

    val subscriptions by remember(dao) { dao.getAll() }.collectAsState(initial = emptyList())

    var currentScreen by remember { mutableStateOf("main") }
    var selectedSubscriptionId by remember { mutableStateOf<Int?>(null) }
    var subscriptionToEdit by remember { mutableStateOf<Subscription?>(null) }

    when (currentScreen) {
        "main" -> {
            MainScreen(
                subscriptions = subscriptions,
                onAddClick = {
                    subscriptionToEdit = null
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
                    scope.launch {
                        if (subscription.id == 0) {
                            dao.insert(subscription)
                        } else {
                            dao.update(subscription)
                        }
                        currentScreen = "main"
                    }
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
                        subscriptionToEdit = sub
                        currentScreen = "add_edit"
                    },
                    onDelete = { id ->
                        currentScreen = "main"
                        scope.launch {
                            dao.deleteById(id)
                        }
                    },
                    onToggleActive = { updatedSub ->
                        scope.launch {
                            dao.update(updatedSub)
                        }
                    },
                    onKeepSubscription = { updatedSub ->
                        scope.launch {
                            dao.update(updatedSub)
                        }
                    }
                )
            }
        }
    }
}
