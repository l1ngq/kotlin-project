package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.MockData
import com.example.myapplication.domain.Subscription
import com.example.myapplication.domain.actualNextDate
import com.example.myapplication.R
import com.example.myapplication.ui.components.DashboardCard
import com.example.myapplication.ui.components.SubscriptionCard
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    subscriptions: List<Subscription>,
    onSubscriptionClick: (Int) -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    val sortedSubscriptions = subscriptions.sortedBy {
        actualNextDate(it.nextPaymentDate, it.periodicity)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.main_title),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Dark,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.main_fab_add_desc)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 88.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                DashboardCard(subscriptions = subscriptions)
            }

            items(sortedSubscriptions, key = { it.id }) { subscription ->
                SubscriptionCard(
                    subscription = subscription,
                    onClick = { onSubscriptionClick(subscription.id) }
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun MainScreenPreview() {
    MyApplicationTheme {
        MainScreen(subscriptions = MockData.sampleSubscriptions)
    }
}