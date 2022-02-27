package com.narcissus.marketplace.di

import com.narcissus.marketplace.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.UserLocalRepositoryImpl
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts

object ServiceLocator {
    private val productsPreviewsRepository = ProductsPreviewRepositoryImpl()
    val getTopRatedProducts = GetTopRatedProducts(productsPreviewsRepository)
    val getTopSalesProducts = GetTopSalesProducts(productsPreviewsRepository)
    val getRandomProducts = GetRandomProducts(productsPreviewsRepository)

    private val userLocalRepository = UserLocalRepositoryImpl()
    val getRecentlyVisitedProducts = GetRecentlyVisitedProducts(userLocalRepository)
}
