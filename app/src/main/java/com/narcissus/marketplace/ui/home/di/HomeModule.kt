package com.narcissus.marketplace.ui.home.di

import com.narcissus.marketplace.core.navigation.destination.HomeDestination
import org.koin.dsl.module

val homeModule = module {
    factory { HomeDestination }
}
