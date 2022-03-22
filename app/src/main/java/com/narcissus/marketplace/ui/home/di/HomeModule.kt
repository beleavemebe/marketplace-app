package com.narcissus.marketplace.ui.home.di

import com.narcissus.marketplace.core.navigation.NavDestination
import com.narcissus.marketplace.ui.product_details.di.productDetailsDestinationModule
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private const val DESTINATION_HOME = "marketplace-app://home"

object HomeDestination

val homeDestinationModule = module {
    factory(
        qualifier<HomeDestination>()
    ) {
        NavDestination { DESTINATION_HOME }
    }
}

val homeModule = module {
    loadKoinModules(productDetailsDestinationModule)
}
