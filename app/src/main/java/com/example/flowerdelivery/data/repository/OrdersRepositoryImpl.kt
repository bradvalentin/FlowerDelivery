package com.example.flowerdelivery.data.repository

import com.example.flowerdelivery.data.entity.Order
import com.example.flowerdelivery.data.network.ApiUtils.handleApiError
import com.example.flowerdelivery.data.network.ApiUtils.handleSuccess
import com.example.flowerdelivery.data.network.AppResult
import com.example.flowerdelivery.data.network.api.OrdersApiService
import com.example.flowerdelivery.utils.NoConnectivityException

class OrdersRepositoryImpl(private val api: OrdersApiService) : OrdersRepository {

    override suspend fun getAllCountries(): AppResult<ArrayList<Order>> {
        return try {
            val response = api.getAllOrders()
            if (response.isSuccessful) {

                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: NoConnectivityException) {
            AppResult.Error(e)

        } catch (e: Exception) {
            AppResult.Error(e)
        }

    }

}