package com.example.flowerdelivery.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.data.network.AppResult
import com.example.flowerdelivery.data.repository.OrdersRepository
import kotlinx.coroutines.launch

class OrdersViewModel(private val repository: OrdersRepository) : ViewModel() {

    private val showLoading = MutableLiveData<Boolean>()
    fun getShowLoading(): LiveData<Boolean> = showLoading

    private val ordersList = MutableLiveData<ArrayList<Order>>()
    fun getOrdersList(): LiveData<ArrayList<Order>> = ordersList

    private val showError = MutableLiveData<String>()
    fun getShowError(): LiveData<String> = showError

    fun getAllOrders(withLoading: Boolean = true) {
        showLoading.value = withLoading
        viewModelScope.launch {
            val result = repository.getAllCountries()

            showLoading.value = false
            when (result) {
                is AppResult.Success -> {
                    ordersList.value = result.successData
                    showError.value = null
                }
                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }
}