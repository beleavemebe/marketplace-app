package com.narcissus.marketplace.ui.splash.di

import com.narcissus.marketplace.core.navigation.destination.MarketplaceUnavailableDestination
import com.narcissus.marketplace.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    factory { MarketplaceUnavailableDestination }
    viewModel { SplashViewModel(getApiStatus = get()) }
}
