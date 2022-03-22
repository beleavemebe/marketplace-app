package com.narcissus.marketplace.ui.cart.di

import com.narcissus.marketplace.core.navigation.destination.CartDestination
import org.koin.dsl.module

val cartModule = module {
    factory { CartDestination }
}
