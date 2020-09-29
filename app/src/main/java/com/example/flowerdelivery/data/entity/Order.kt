package com.example.flowerdelivery.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val id: Int,
    val contact: Contact,
    val delivery_date: String,
    val delivery_time: String,
    val distance: Int,
    val greeting_card: String?,
    val image_url: String,
    val name: String,
    val price: Int
) : Parcelable