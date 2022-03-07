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

// TODO: replace with Koin direct injection
object ServiceLocator : KoinComponent {
    private val productsDetailsRepository: ProductsDetailsRepository by inject()
    val getProductDetails = GetProductDetails(productsDetailsRepository)

    private val cartLocalRepositoryImpl: CartLocalRepository by inject()
    val getCart = GetCart(cartLocalRepositoryImpl)
    val getCartCost = GetCartCost(cartLocalRepositoryImpl)
    val getCartItemsAmount = GetCartItemsAmount(cartLocalRepositoryImpl)
    val removeFromCart = RemoveFromCart(cartLocalRepositoryImpl)
    val setCartItemSelected = SetCartItemSelected(cartLocalRepositoryImpl)
    val setCartItemAmount = SetCartItemAmount(cartLocalRepositoryImpl)
}
