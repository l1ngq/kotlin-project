package com.example.myapplication.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val cost: Int,
    val currency: String = "₽",
    val periodicity: String = "Раз в месяц",
    val nextPaymentDate: String,
    val isTrial: Boolean = false,
    val trialEndDate: String? = null,
    val isActive: Boolean = true,
    val remindSetting: String = "За 1 день",
    val note: String = "",
    val color: Long = 0xFF007AFF,
    val iconCategory: String? = null
)
