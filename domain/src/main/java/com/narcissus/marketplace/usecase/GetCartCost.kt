package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartCost(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): Flow<String> { // temporary implementation
        return flow {
            val listFlow = cartRepository.getCart()
            listFlow.collect { listItems ->
                var sum = 0
                if (listItems.isEmpty()) {
                    emit("")
                } else {
                    for (item in listItems) {
                        if (item.isSelected) {
                            sum += item.data.price * item.count
                        }
                    }
                    emit("$$sum")
                }
            }
        }
    }
}
