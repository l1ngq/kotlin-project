package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.MockData
import com.example.myapplication.domain.Subscription
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.MyApplicationTheme

private fun monthlyCost(subscription: Subscription): Int = when (subscription.periodicity) {
    "weekly" -> subscription.cost * 4
    "yearly" -> subscription.cost / 12
    else -> subscription.cost
}

@Composable
fun DashboardCard(
    subscriptions: List<Subscription>,
    modifier: Modifier = Modifier
) {
    var isYearly by remember { mutableStateOf(false) }

    val activeSubscriptions = subscriptions.filter { it.isActive }

    val paidSubscriptions = activeSubscriptions.filter { !it.isTrial }

    val grouped = paidSubscriptions.groupBy { it.currency }

    val totals = grouped.map { entry ->
        val monthly = entry.value.sumOf { monthlyCost(it) }
        val total = if (isYearly) monthly * 12 else monthly
        "$total ${entry.key}"
    }

    val totalLines = if (totals.isEmpty()) listOf("0 ₽") else totals

    val activeCount = paidSubscriptions.size
    val trialCount = activeSubscriptions.count { it.isTrial }

    val chipColors = FilterChipDefaults.filterChipColors(
        containerColor = Color.Transparent,
        labelColor = Color.White.copy(alpha = 0.7f),
        selectedContainerColor = Color.White,
        selectedLabelColor = Dark
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Dark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dashboard_total_expenses),
                    fontSize = 14.sp,
                    color = Color.White
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(
                        selected = !isYearly,
                        onClick = { isYearly = false },
                        label = { Text(stringResource(R.string.dashboard_per_month), fontSize = 12.sp) },
                        colors = chipColors
                    )
                    FilterChip(
                        selected = isYearly,
                        onClick = { isYearly = true },
                        label = { Text(stringResource(R.string.dashboard_per_year), fontSize = 12.sp) },
                        colors = chipColors
                    )
                }
            }

            totalLines.forEach { line ->
                Text(
                    text = line,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = stringResource(R.string.dashboard_summary, activeCount, trialCount),
                fontSize = 13.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun DashboardCardPreview() {
    MyApplicationTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DashboardCard(subscriptions = MockData.sampleSubscriptions)
        }
    }
}