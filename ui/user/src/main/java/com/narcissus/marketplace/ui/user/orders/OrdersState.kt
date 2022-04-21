package com.narcissus.marketplace.ui.user.orders

import com.narcissus.marketplace.domain.model.orders.Order

data class OrdersState(
    val isLoading: Boolean,
    val orders: List<Order> = emptyList()
)
