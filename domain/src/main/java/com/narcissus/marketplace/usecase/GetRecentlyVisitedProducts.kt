package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.local.UserLocalRepository

class GetRecentlyVisitedProducts(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke()= userLocalRepository.getRecentlyVisitedProducts()
}