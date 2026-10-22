package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.MockData
import com.example.myapplication.domain.Subscription
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple

@Composable
fun SubscriptionCard(
    subscription: Subscription,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val icon = iconForCategory(subscription.iconCategory)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = Color(subscription.color),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
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

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = subscription.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (!subscription.isActive) {
                    Text(
                        text = stringResource(R.string.status_paused),
                        color = Blue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else if (subscription.isTrial) {
                    Text(
                        text = stringResource(
                            R.string.card_trial_until,
                            subscription.trialEndDate ?: subscription.nextPaymentDate
                        ),
                        color = Purple,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Text(
                        text = stringResource(R.string.card_next_payment, subscription.nextPaymentDate),
                        fontSize = 13.sp
                    )
                }
            }

            Text(
                text = "${subscription.cost} ${subscription.currency}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionCardPreview() {
    MyApplicationTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SubscriptionCard(subscription = MockData.sampleSubscriptions[0])
            SubscriptionCard(subscription = MockData.sampleSubscriptions[1])
            SubscriptionCard(subscription = MockData.sampleSubscriptions[3])
        }
    }
}