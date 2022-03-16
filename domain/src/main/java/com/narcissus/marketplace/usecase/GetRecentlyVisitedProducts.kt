package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.UserRepository

class GetRecentlyVisitedProducts(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getRecentlyVisitedProducts()
}
