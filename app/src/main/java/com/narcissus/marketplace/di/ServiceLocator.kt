package com.narcissus.marketplace.di

import com.narcissus.marketplace.CartLocalRepositoryImpl
import com.narcissus.marketplace.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.UserLocalRepositoryImpl
import com.narcissus.marketplace.usecase.*

object ServiceLocator {
    private val productsPreviewsRepository = ProductsPreviewRepositoryImpl()
    val getTopRatedProducts = GetTopRatedProducts(productsPreviewsRepository)
    val getTopSalesProducts = GetTopSalesProducts(productsPreviewsRepository)
    val getRandomProducts = GetRandomProducts(productsPreviewsRepository)

    private val userLocalRepository = UserLocalRepositoryImpl()
    val getRecentlyVisitedProducts = GetRecentlyVisitedProducts(userLocalRepository)

    private val cartLocalRepositoryImpl = CartLocalRepositoryImpl()
    val getCart = GetCart(cartLocalRepositoryImpl)
    val getCartCost = GetCartCost(cartLocalRepositoryImpl)
}
