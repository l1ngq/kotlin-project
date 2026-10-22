package com.example.myapplication.data

import com.example.myapplication.domain.Subscription

object MockData {
    val sampleSubscriptions = listOf(

        Subscription(
            id = 2,
            title = "ChatGPT Plus",
            cost = 2990,
            nextPaymentDate = "30 октября",
            isTrial = true,
            trialEndDate = "30 октября",
            color = 0xFFAF52DE,
            iconCategory = "work"
        ),

        Subscription(
            id = 1,
            title = "Яндекс Плюс",
            cost = 399,
            nextPaymentDate = "15 ноября",
            color = 0xFFFF9500,
            iconCategory = "music",
            note = "Карта Т-Банк"
        ),

        Subscription(
            id = 3,
            title = "Telegram Premium",
            cost = 299,
            nextPaymentDate = "5 декабря",
            color = 0xFF007AFF
        ),

        Subscription(
            id = 4,
            title = "Спортзал",
            cost = 2500,
            nextPaymentDate = "1 декабря",
            isActive = false,
            color = 0xFF34C759,
            iconCategory = "sport",
            note = "Заморозка до зимы"
        )
    )
}