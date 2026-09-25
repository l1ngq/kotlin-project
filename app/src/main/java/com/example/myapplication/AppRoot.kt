package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.screens.CalculatorScreen

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    CalculatorScreen(modifier = modifier)
}
