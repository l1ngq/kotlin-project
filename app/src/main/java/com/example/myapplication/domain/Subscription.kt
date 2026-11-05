package com.example.myapplication.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val cost: Int,
    val currency: String = "₽",
    val periodicity: String,
    val nextPaymentDate: LocalDate,
    val isTrial: Boolean = false,
    val trialEndDate: LocalDate? = null,
    val isActive: Boolean = true,
    val remindSetting: String,
    val note: String = "",
    val color: Long = 0xFF007AFF,
    val iconCategory: String? = null
)

fun monthlyCost(subscription: Subscription): Int = when (subscription.periodicity) {
    "weekly" -> subscription.cost * 4
    "yearly" -> subscription.cost / 12
    else -> subscription.cost
}

fun yearlyCost(subscription: Subscription): Int = when (subscription.periodicity) {
    "yearly" -> subscription.cost
    else -> monthlyCost(subscription) * 12
}

fun totalsByCurrency(subscriptions: List<Subscription>, isYearly: Boolean): Map<String, Int> {
    val paid = subscriptions.filter { it.isActive && !it.isTrial }
    val grouped = paid.groupBy { it.currency }
    return grouped.mapValues { entry ->
        if (isYearly) {
            entry.value.sumOf { yearlyCost(it) }
        } else {
            entry.value.sumOf { monthlyCost(it) }
        }
    }
}

fun isTrialExpired(subscription: Subscription): Boolean {
    val end = subscription.trialEndDate ?: subscription.nextPaymentDate
    return end.isBefore(LocalDate.now())
}

fun needsReminder(subscription: Subscription): Boolean {
    val days = when (subscription.remindSetting) {
        "on_payment_day" -> 0
        "three_days_before" -> 3
        else -> 1
    }
    val date = actualNextDate(subscription.nextPaymentDate, subscription.periodicity)
    return date == LocalDate.now().plusDays(days.toLong())
}
