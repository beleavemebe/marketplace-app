package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.Order
import com.narcissus.marketplace.domain.model.OrderPaymentResult
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    suspend fun payForTheOrder(orderList: List<CartItem>): OrderPaymentResult
    suspend fun saveOrder(order: Order)
}
