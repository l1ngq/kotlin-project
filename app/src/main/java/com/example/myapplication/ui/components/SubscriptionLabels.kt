package com.example.myapplication.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

@Composable
fun periodLabel(period: String): String = stringResource(
    when (period) {
        "weekly" -> R.string.period_weekly
        "monthly" -> R.string.period_monthly
        "yearly" -> R.string.period_yearly
        else -> R.string.period_monthly
    }
)

@Composable
fun reminderLabel(reminder: String): String = stringResource(
    when (reminder) {
        "on_payment_day" -> R.string.reminder_on_day
        "one_day_before" -> R.string.reminder_1_day_before
        "three_days_before" -> R.string.reminder_3_days_before
        else -> R.string.reminder_1_day_before
    }
)
