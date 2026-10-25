package com.example.myapplication.data

import com.example.myapplication.domain.Subscription

object MockData {
    val sampleSubscriptions = listOf(

        Subscription(
            id = 2,
            title = "ChatGPT Plus",
            cost = 2990,
            periodicity = "monthly",
            nextPaymentDate = "30 октября",
            remindSetting = "one_day_before",
            isTrial = true,
            trialEndDate = "30 октября",
            color = 0xFFAF52DE,
            iconCategory = "work"
        ),

        Subscription(
            id = 1,
            title = "Яндекс Плюс",
            cost = 399,
            periodicity = "monthly",
            nextPaymentDate = "15 ноября",
            remindSetting = "one_day_before",
            color = 0xFFFF9500,
            iconCategory = "music",
            note = "Карта Т-Банк"
        ),

        Subscription(
            id = 3,
            title = "Telegram Premium",
            cost = 299,
            periodicity = "monthly",
            nextPaymentDate = "5 декабря",
            remindSetting = "one_day_before",
            color = 0xFF007AFF
        ),

        Subscription(
            id = 4,
            title = "Спортзал",
            cost = 2500,
            periodicity = "monthly",
            nextPaymentDate = "1 декабря",
            remindSetting = "one_day_before",
            isActive = false,
            color = 0xFF34C759,
            iconCategory = "sport",
            note = "Заморозка до зимы"
        )
    )
}