package com.example.myapplication.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myapplication.domain.needsReminder
import java.util.concurrent.TimeUnit

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val dao = AppDatabase.getInstance(applicationContext).subscriptionDao()
        val due = dao.getAllOnce().filter { it.isActive && needsReminder(it) }
        showReminder(applicationContext, due)
        return Result.success()
    }
}

fun scheduleReminders(context: Context) {
    val request = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "reminders",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}
