package com.example.myapplication.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.domain.Subscription
import com.example.myapplication.domain.actualNextDate
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).subscriptionDao()

    val subscriptions = dao.getAll()

    fun save(subscription: Subscription) = viewModelScope.launch {
        if (subscription.id == 0) {
            dao.insert(subscription)
        } else {
            dao.update(subscription)
        }
    }

    fun delete(id: Int) = viewModelScope.launch {
        dao.deleteById(id)
    }

    fun toggleActive(subscription: Subscription) = viewModelScope.launch {
        dao.update(subscription.copy(isActive = !subscription.isActive))
    }

    fun keepAsPaid(subscription: Subscription) = viewModelScope.launch {
        dao.update(
            subscription.copy(
                isTrial = false,
                trialEndDate = null,
                isActive = true,
                nextPaymentDate = actualNextDate(
                    subscription.nextPaymentDate,
                    subscription.periodicity
                )
            )
        )
    }
}
