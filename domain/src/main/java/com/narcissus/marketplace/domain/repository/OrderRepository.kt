package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrders(): Flow<List<Order>>
    suspend fun makeAnOrder(order: Order): Boolean
}
