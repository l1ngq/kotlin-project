package com.example.myapplication.data

object RandomGenerator {
    fun createList(): List<Int> {
        return List(10) { (-50..50).random() }
    }
}