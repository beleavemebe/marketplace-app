package com.narcissus.marketplace.domain.model

import com.narcissus.marketplace.domain.model.orders.Order

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val cart: Cart,
    val orders: List<Order>,
    val cartNumber: Long,
)
