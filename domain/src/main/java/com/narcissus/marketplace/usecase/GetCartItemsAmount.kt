package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCartItemsAmount(private val cartRepository: CartRepository) {
    suspend operator fun invoke(): Flow<String> { // temporary implementation
        return flow {
            val listFlow = cartRepository.getCart()
            listFlow.collect { listItems ->
                if (listItems.isEmpty()) {
                    emit("")
                } else {
                    var amount = 0
                    for (item in listItems) {
                        if (item.isSelected) {
                            amount += item.count
                        }
                    }
                    emit("goods: $amount")
                }
            }
        }
    }
}
