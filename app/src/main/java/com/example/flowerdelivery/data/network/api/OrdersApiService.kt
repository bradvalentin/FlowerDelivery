package com.example.flowerdelivery.data.network.api

import com.example.flowerdelivery.data.entity.Order
import retrofit2.Response
import retrofit2.http.GET

interface OrdersApiService {

    @GET("orders")
    suspend fun getAllOrders(): Response<ArrayList<Order>>

}