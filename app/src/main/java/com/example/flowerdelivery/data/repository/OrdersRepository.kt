package com.example.flowerdelivery.data.repository

import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.data.network.AppResult

interface OrdersRepository {
    suspend fun getAllCountries() : AppResult<ArrayList<Order>>
}