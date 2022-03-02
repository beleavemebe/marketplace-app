package com.narcissus.marketplace.di

import com.narcissus.marketplace.CartLocalRepositoryImpl
import com.narcissus.marketplace.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.UserLocalRepositoryImpl
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount

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
    val getCartItemsAmount = GetCartItemsAmount(cartLocalRepositoryImpl)
}
