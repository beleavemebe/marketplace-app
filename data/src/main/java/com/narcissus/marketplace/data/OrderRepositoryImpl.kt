package com.narcissus.marketplace.data

import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.repository.OrderRepository
import com.narcissus.marketplace.domain.util.ActionResult
import kotlinx.coroutines.flow.Flow

internal class OrderRepositoryImpl : OrderRepository {
    override suspend fun getOrders(): Flow<List<Order>> {
        TODO("Not yet implemented")
    }

    override suspend fun makeAnOrder(order: Order): Boolean {
        TODO("Not yet implemented")
    }
}
