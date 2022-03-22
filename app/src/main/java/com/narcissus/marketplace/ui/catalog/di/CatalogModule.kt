package com.narcissus.marketplace.ui.catalog.di

import com.narcissus.marketplace.core.navigation.NavDestination
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private const val DESTINATION_CATALOG = "marketplace-app://catalog"

object CatalogDestination

val catalogDestinationModule = module {
    factory(
        qualifier<CatalogDestination>()
    ) {
        NavDestination { DESTINATION_CATALOG }
    }
}
