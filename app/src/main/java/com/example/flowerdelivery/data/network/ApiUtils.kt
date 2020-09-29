package com.example.flowerdelivery.data.network

import com.example.flowerdelivery.data.network.api.ApiError
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiUtils {

    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
        val error = parseError(resp)
        return AppResult.Error(Exception(error.message))
    }

    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response)
    }

    private fun parseError(response: Response<*>): ApiError {

        val gson = GsonBuilder().create()
        val error: ApiError

        try {
            error = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        } catch (e: IOException) {
            return ApiError(e.localizedMessage)
        }
        return error
    }
}