package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.local.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartCost(private val cartLocalRepository: CartLocalRepository) {
    suspend operator fun invoke(): Flow<String> { // temporary implementation
        return flow {
            val listFlow = cartLocalRepository.getCart()
            listFlow.collect { listItems ->
                var sum = 0
                if (listItems.isEmpty()) {
                    emit("")
                } else {
                    for (item in listItems) {
                        sum += item.data.price
                    }
                    emit("$$sum")
                }
            }
        }
    }
}
