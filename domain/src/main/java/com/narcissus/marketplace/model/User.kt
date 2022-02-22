package com.narcissus.marketplace.model

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val cart: Cart,
    val orders: List<Order>,
    val cartNumber: Long
)
