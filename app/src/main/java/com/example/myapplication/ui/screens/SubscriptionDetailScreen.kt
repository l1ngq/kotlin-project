package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.myapplication.ui.components.iconForCategory
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.Green
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Orange
import com.example.myapplication.ui.theme.Purple
import com.example.myapplication.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionDetailScreen(
    subscription: Subscription,
    onBack: () -> Unit = {},
    onEdit: (Subscription) -> Unit = {},
    onDelete: (Int) -> Unit = {},
    onToggleActive: (Subscription) -> Unit = {},
    onKeepSubscription: (Subscription) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val statusText = when {
                !subscription.isActive -> stringResource(R.string.status_paused)
                subscription.isTrial -> stringResource(R.string.status_trial)
                else -> stringResource(R.string.status_active)
            }
            val statusColor = when {
                !subscription.isActive -> Blue
                subscription.isTrial -> Purple
                else -> Green
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Dark)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(subscription.color), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            val icon = iconForCategory(subscription.iconCategory)
                            if (icon != null) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            } else {
                                Text(
                                    text = subscription.title.take(1).uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Column {
                            Text(
                                text = subscription.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = statusText,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = statusColor
                            )
                        }
                    }

                    Text(
                        text = "${subscription.cost} ${subscription.currency}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = subscription.periodicity,
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                }
            }

            if (subscription.isTrial && subscription.isActive) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = null,
                                tint = Orange
                            )
                            Text(
                                text = stringResource(R.string.detail_trial_warning_title),
                                fontWeight = FontWeight.Bold,
                                color = Dark
                            )
                        }

                        Text(
                            text = stringResource(R.string.detail_trial_warning_desc),
                            fontSize = 13.sp
                        )

                        Button(
                            onClick = {
                                onKeepSubscription(
                                    subscription.copy(isTrial = false, trialEndDate = null, isActive = true)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Dark,
                                contentColor = Color.White
                            )
                        ) {
                            Text(stringResource(R.string.detail_btn_keep), fontSize = 13.sp)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = stringResource(R.string.detail_section_info), fontWeight = FontWeight.Bold, fontSize = 15.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(
                                if (subscription.isTrial) R.string.detail_label_trial_end else R.string.detail_label_next_payment
                            ),
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (subscription.isTrial) (subscription.trialEndDate ?: subscription.nextPaymentDate) else subscription.nextPaymentDate,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(R.string.detail_label_period), color = Color.Gray, fontSize = 13.sp)
                        Text(text = subscription.periodicity, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(R.string.detail_label_reminder), color = Color.Gray, fontSize = 13.sp)
                        Text(text = subscription.remindSetting, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    }

                    if (subscription.note.isNotBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(R.string.detail_label_note), color = Color.Gray, fontSize = 13.sp)
                            Text(text = subscription.note, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                        }
                    }
                }
            }

            Button(
                onClick = { onToggleActive(subscription.copy(isActive = !subscription.isActive)) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Dark
                )
            ) {
                Text(stringResource(if (subscription.isActive) R.string.detail_btn_pause else R.string.detail_btn_resume))
            }

            Button(
                onClick = { onEdit(subscription) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Dark,
                    contentColor = Color.White
                )
            ) {
                Text(stringResource(R.string.detail_btn_edit))
            }

            OutlinedButton(
                onClick = { onDelete(subscription.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Red
                )
            ) {
                Text(stringResource(R.string.detail_btn_delete))
            }
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun SubscriptionDetailActivePreview() {
    MyApplicationTheme {
        SubscriptionDetailScreen(
            subscription = MockData.sampleSubscriptions[1]
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun SubscriptionDetailTrialPreview() {
    MyApplicationTheme {
        SubscriptionDetailScreen(
            subscription = MockData.sampleSubscriptions[0]
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun SubscriptionDetailPausedPreview() {
    MyApplicationTheme {
        SubscriptionDetailScreen(
            subscription = MockData.sampleSubscriptions[3]
        )
    }
}