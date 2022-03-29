package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.util.ActionResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    suspend fun makeAnOrder(order: Order): ActionResult<Boolean>
}
