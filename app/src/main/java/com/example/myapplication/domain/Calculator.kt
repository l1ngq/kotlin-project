package com.example.myapplication.domain

import com.example.myapplication.data.ResultFormatter

data class CalculationResult (
    val negativeSum: Int,
    val positiveCount: Int,
)

object Calculator {
    fun calculate(input: List<Int>): CalculationResult {
        val negativeSum = input.filter { it < 0 }.sumOf { it }
        val positiveCount = input.count { it > 0 }
        return CalculationResult(negativeSum, positiveCount)
    }

    fun runCalculation(input: List<Int>): String {
        val result = calculate(input)
        return ResultFormatter.format(result)
    }
}