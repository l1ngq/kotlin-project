package com.example.myapplication.domain

data class Subscription(
    val id: Int,
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