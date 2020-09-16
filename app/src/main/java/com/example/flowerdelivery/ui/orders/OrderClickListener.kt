package com.example.flowerdelivery.ui.orders

import com.example.flowerdelivery.data.entity.Order

interface OrderClickListener {
    fun onItemClick(order: Order)

}