package com.example.myapplication.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.domain.Subscription

fun showReminder(context: Context, subscriptions: List<Subscription>) {
    if (subscriptions.isEmpty()) return

    val manager = context.getSystemService(NotificationManager::class.java)
    manager.createNotificationChannel(
        NotificationChannel(
            "reminders",
            context.getString(R.string.notification_channel),
            NotificationManager.IMPORTANCE_DEFAULT
        )
    )

    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = Notification.Builder(context, "reminders")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(subscriptions.joinToString(", ") { it.title })
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    manager.notify(1, notification)
}
