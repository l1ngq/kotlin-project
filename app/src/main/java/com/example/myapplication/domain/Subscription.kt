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
