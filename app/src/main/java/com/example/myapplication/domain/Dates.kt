package com.example.myapplication.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy")

fun parseDate(text: String): LocalDate? = try {
    LocalDate.parse(text.trim(), dateFormat)
} catch (e: DateTimeParseException) {
    null
}

fun formatDate(date: LocalDate?): String = if (date == null) "" else date.format(dateFormat)

private fun nextDate(date: LocalDate, periodicity: String): LocalDate = when (periodicity) {
    "weekly" -> date.plusWeeks(1)
    "yearly" -> date.plusYears(1)
    else -> date.plusMonths(1)
}

fun actualNextDate(date: LocalDate, periodicity: String): LocalDate {
    var result = date
    val today = LocalDate.now()
    while (result.isBefore(today)) {
        result = nextDate(result, periodicity)
    }
    return result
}
