package com.narcissus.marketplace.ui.cart.di

import com.narcissus.marketplace.core.navigation.NavDestination
import com.narcissus.marketplace.ui.catalog.di.catalogDestinationModule
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private const val DESTINATION_CART = "marketplace-app://cart"

object CartDestination

val cartDestinationModule = module {
    factory(
        qualifier<CartDestination>()
    ) {
        NavDestination { DESTINATION_CART }
    }
}

val cartModule = module {
    loadKoinModules(catalogDestinationModule)
}
