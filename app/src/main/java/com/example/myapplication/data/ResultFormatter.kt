package com.example.myapplication.data

import com.example.myapplication.domain.CalculationResult

object ResultFormatter {
    fun format(result: CalculationResult): String {
        return "Сумма отрицательных: ${result.negativeSum}\nКоличество положительных: ${result.positiveCount}"
    }
}