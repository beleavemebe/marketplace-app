package com.narcissus.marketplace.ui.product_details.di

import com.narcissus.marketplace.core.navigation.destination.ProductDetailsDestination
import org.koin.dsl.module

val productDetailsModule = module {
    factory { (productId: String) ->
        ProductDetailsDestination(productId)
    }
}
