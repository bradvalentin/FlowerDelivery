package com.example.flowerdelivery.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    val address: String,
    val deliver_to: String,
    val phone: String
) : Parcelable