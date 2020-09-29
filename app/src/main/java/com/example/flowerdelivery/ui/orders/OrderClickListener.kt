package com.example.flowerdelivery.ui.orders

import android.widget.ImageView
import android.widget.TextView
import com.example.flowerdelivery.data.entity.Order

interface OrderClickListener {
    fun onItemClick(order: Order, imageView: ImageView,orderItemName: TextView, costumerName: TextView, distance: TextView)
}