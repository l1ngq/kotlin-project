package com.example.myapplication.data

import com.example.myapplication.domain.Subscription
import java.time.LocalDate

object MockData {
    val sampleSubscriptions = listOf(

        Subscription(
            id = 2,
            title = "ChatGPT Plus",
            cost = 2990,
            periodicity = "monthly",
            nextPaymentDate = LocalDate.of(2026, 10, 30),
            remindSetting = "one_day_before",
            isTrial = true,
            trialEndDate = LocalDate.of(2026, 10, 30),
            color = 0xFFAF52DE,
            iconCategory = "work"
        ),

        Subscription(
            id = 1,
            title = "Яндекс Плюс",
            cost = 399,
            periodicity = "monthly",
            nextPaymentDate = LocalDate.of(2026, 11, 15),
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
            nextPaymentDate = LocalDate.of(2026, 12, 5),
            remindSetting = "one_day_before",
            color = 0xFF007AFF
        ),

        Subscription(
            id = 4,
            title = "Спортзал",
            cost = 2500,
            periodicity = "monthly",
            nextPaymentDate = LocalDate.of(2026, 12, 1),
            remindSetting = "one_day_before",
            isActive = false,
            color = 0xFF34C759,
            iconCategory = "sport",
            note = "Заморозка до зимы"
        )
    )
}