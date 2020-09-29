package com.example.flowerdelivery.data.network

sealed class AppResult<out T> {

    data class Success<out T>(val successData : T) : AppResult<T>()
    class Error(val exception: java.lang.Exception) : AppResult<Nothing>()

}