package com.example.myapplication.domain

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
}