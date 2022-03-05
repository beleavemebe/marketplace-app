package com.narcissus.marketplace.di

import com.narcissus.marketplace.repository.local.CartLocalRepository
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.GetProductDetails
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO: replace with Koin direct injection
object ServiceLocator : KoinComponent {
    private val productsDetailsRepository: ProductsDetailsRepository by inject()
    val getProductDetails = GetProductDetails(productsDetailsRepository)

    private val cartLocalRepositoryImpl: CartLocalRepository by inject()
    val getCart = GetCart(cartLocalRepositoryImpl)
    val getCartCost = GetCartCost(cartLocalRepositoryImpl)
    val getCartItemsAmount = GetCartItemsAmount(cartLocalRepositoryImpl)
}
