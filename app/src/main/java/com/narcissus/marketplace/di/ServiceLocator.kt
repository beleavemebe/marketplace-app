package com.narcissus.marketplace.di

import com.narcissus.marketplace.repository.local.CartLocalRepository
import com.narcissus.marketplace.repository.local.UserLocalRepository
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.GetProductDetails
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO: replace with Koin direct injection
object ServiceLocator : KoinComponent {
    private val productsDetailsRepository: ProductsDetailsRepository by inject()
    val getProductDetails = GetProductDetails(productsDetailsRepository)

    private val productsPreviewsRepository: ProductsPreviewRepository by inject()
    val getTopRatedProducts = GetTopRatedProducts(productsPreviewsRepository)
    val getTopSalesProducts = GetTopSalesProducts(productsPreviewsRepository)
    val getRandomProducts = GetRandomProducts(productsPreviewsRepository)

    private val userLocalRepository: UserLocalRepository by inject()
    val getRecentlyVisitedProducts = GetRecentlyVisitedProducts(userLocalRepository)

    private val cartLocalRepositoryImpl: CartLocalRepository by inject()
    val getCart = GetCart(cartLocalRepositoryImpl)
    val getCartCost = GetCartCost(cartLocalRepositoryImpl)
    val getCartItemsAmount = GetCartItemsAmount(cartLocalRepositoryImpl)
}
