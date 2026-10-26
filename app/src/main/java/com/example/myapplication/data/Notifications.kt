package com.example.myapplication.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.myapplication.R
import com.example.myapplication.domain.Subscription
import com.example.myapplication.domain.needsReminder

fun showReminder(context: Context, subscriptions: List<Subscription>) {
    val due = subscriptions.filter { it.isActive && needsReminder(it) }
    if (due.isEmpty()) return

    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(
        NotificationChannel(
            "reminders",
            context.getString(R.string.notification_channel),
            NotificationManager.IMPORTANCE_DEFAULT
        )
    )

    val notification = Notification.Builder(context, "reminders")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(due.joinToString(", ") { it.title })
        .setAutoCancel(true)
        .build()

    manager.notify(1, notification)
}
