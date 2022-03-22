package com.narcissus.marketplace.ui.catalog.di

import com.narcissus.marketplace.core.navigation.destination.CatalogDestination
import org.koin.dsl.module

val catalogModule = module {
    factory { CatalogDestination }
}
