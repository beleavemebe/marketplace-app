package com.narcissus.marketplace.di

import com.narcissus.marketplace.data.CartLocalRepositoryImpl
import com.narcissus.marketplace.data.ProductsDetailsRepositoryImpl
import com.narcissus.marketplace.data.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.data.UserLocalRepositoryImpl
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.GetProductDetails

object ServiceLocator {
    private val productsDetailsRepository = ProductsDetailsRepositoryImpl()
    val getProductDetails = GetProductDetails(productsDetailsRepository)

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
