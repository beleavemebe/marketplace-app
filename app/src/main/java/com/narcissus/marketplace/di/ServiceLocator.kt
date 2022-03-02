package com.narcissus.marketplace.di

import com.narcissus.marketplace.data.ProductsDetailsRepositoryImpl
import com.narcissus.marketplace.data.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.data.UserLocalRepositoryImpl
import com.narcissus.marketplace.usecase.GetProductDetails
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
    private val productDetailsRepository = ProductsDetailsRepositoryImpl()
    val getProductDetails = GetProductDetails(productDetailsRepository)
}
