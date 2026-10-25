package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.domain.Subscription
import com.example.myapplication.ui.components.periodLabel
import com.example.myapplication.ui.components.reminderLabel
import com.example.myapplication.ui.components.subscriptionCategories
import com.example.myapplication.ui.theme.Dark
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.subscriptionColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSubscriptionScreen(
    subscriptionToEdit: Subscription? = null,
    onSave: (Subscription) -> Unit = {},
    onBack: () -> Unit = {}
) {
    var title by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.title ?: "") }
    var costText by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.cost?.toString() ?: "") }
    var currency by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.currency ?: "₽") }
    var periodicity by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.periodicity ?: "monthly") }
    var nextPaymentDate by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.nextPaymentDate ?: "") }
    var isTrial by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.isTrial ?: false) }
    var remindSetting by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.remindSetting ?: "one_day_before") }
    var note by remember(subscriptionToEdit) { mutableStateOf(subscriptionToEdit?.note ?: "") }

    var selectedColor by remember(subscriptionToEdit) {
        mutableStateOf(subscriptionToEdit?.color ?: subscriptionColorPalette.first())
    }
    var selectedIcon by remember(subscriptionToEdit) { mutableStateOf<String?>(subscriptionToEdit?.iconCategory) }

    val availableCurrencies = listOf("₽", "$")
    val availablePeriods = listOf("weekly", "monthly", "yearly")
    val availableReminders = listOf("on_payment_day", "one_day_before", "three_days_before")

    val chipColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = Dark,
        selectedLabelColor = Color.White,
        selectedLeadingIconColor = Color.White
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(if (subscriptionToEdit == null) R.string.add_title_new else R.string.add_title_edit),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.field_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = costText,
                    onValueChange = { costText = it },
                    label = { Text(stringResource(R.string.field_cost_label)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                availableCurrencies.forEach { curr ->
                    FilterChip(
                        selected = currency == curr,
                        onClick = { currency = curr },
                        label = { Text(curr) },
                        colors = chipColors
                    )
                }
            }

            Text(text = stringResource(R.string.field_period_label), fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                availablePeriods.forEach { period ->
                    FilterChip(
                        selected = periodicity == period,
                        onClick = { periodicity = period },
                        label = { Text(periodLabel(period), fontSize = 11.sp) },
                        colors = chipColors
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.field_trial_label), fontSize = 15.sp)
                Switch(
                    checked = isTrial,
                    onCheckedChange = { isTrial = it }
                )
            }

            OutlinedTextField(
                value = nextPaymentDate,
                onValueChange = { nextPaymentDate = it },
                label = { Text(stringResource(if (isTrial) R.string.field_date_trial else R.string.field_date_payment)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(text = stringResource(R.string.field_color_label), fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                subscriptionColorPalette.forEach { colorLong ->
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color(colorLong), shape = CircleShape)
                            .clickable { selectedColor = colorLong }
                            .then(
                                if (selectedColor == colorLong) {
                                    Modifier.border(2.dp, Dark, CircleShape)
                                } else Modifier
                            )
                    )
                }
            }

            Text(text = stringResource(R.string.field_category_label), fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                subscriptionCategories.forEach { category ->
                    FilterChip(
                        selected = selectedIcon == category.key,
                        onClick = {
                            selectedIcon = if (selectedIcon == category.key) null else category.key
                        },
                        label = {
                            Icon(
                                imageVector = category.icon,
                                contentDescription = category.key,
                                modifier = Modifier.size(20.dp),
                                tint = if (selectedIcon == category.key) Color.White else Dark
                            )
                        },
                        colors = chipColors
                    )
                }
            }

            Text(text = stringResource(R.string.field_reminder_label), fontSize = 13.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                availableReminders.forEach { rem ->
                    FilterChip(
                        selected = remindSetting == rem,
                        onClick = { remindSetting = rem },
                        label = { Text(reminderLabel(rem), fontSize = 11.sp) },
                        colors = chipColors
                    )
                }
            }

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text(stringResource(R.string.field_note_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val result = Subscription(
                        id = subscriptionToEdit?.id ?: 0,
                        title = title,
                        cost = costText.toIntOrNull() ?: 0,
                        currency = currency,
                        periodicity = periodicity,
                        nextPaymentDate = nextPaymentDate,
                        isTrial = isTrial,
                        trialEndDate = if (isTrial) nextPaymentDate else null,
                        remindSetting = remindSetting,
                        note = note,
                        color = selectedColor,
                        iconCategory = selectedIcon,
                        isActive = subscriptionToEdit?.isActive ?: true
                    )
                    onSave(result)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Dark,
                    contentColor = Color.White,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Gray
                ),
                enabled = title.isNotBlank() &&
                    (costText.toIntOrNull() ?: 0) > 0 &&
                    nextPaymentDate.isNotBlank()
            ) {
                Text(stringResource(R.string.btn_save))
            }
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
fun AddEditSubscriptionScreenPreview() {
    MyApplicationTheme {
        AddEditSubscriptionScreen()
    }
}