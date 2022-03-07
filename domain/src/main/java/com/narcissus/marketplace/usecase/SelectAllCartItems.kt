package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.repository.local.CartLocalRepository

class SelectAllCartItems (private val cartLocalRepository: CartLocalRepository){
    suspend operator fun invoke(isSelected:Boolean) = cartLocalRepository.selectAllCartItems(isSelected)
}