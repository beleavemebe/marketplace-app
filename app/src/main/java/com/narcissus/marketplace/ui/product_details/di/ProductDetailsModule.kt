package com.narcissus.marketplace.ui.product_details.di

import com.narcissus.marketplace.core.navigation.NavDestination
import com.narcissus.marketplace.ui.cart.di.cartDestinationModule
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private fun productDetailsDestination(id: String) = "marketplace-app://product/${id}"

object ProductDetailsDestination

val productDetailsDestinationModule = module {
    factory(
        qualifier<ProductDetailsDestination>()
    ) { (productId: String) ->
        NavDestination { productDetailsDestination(productId) }
    }
}

val productDetailsModule = module {
    loadKoinModules(cartDestinationModule)
}
