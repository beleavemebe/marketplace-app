package com.narcissus.marketplace.ui.splash.di

import com.narcissus.marketplace.ui.home.di.homeDestinationModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val splashModule = module {
    loadKoinModules(homeDestinationModule)
}
