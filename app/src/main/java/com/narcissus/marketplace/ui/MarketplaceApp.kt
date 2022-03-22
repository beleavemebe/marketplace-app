package com.narcissus.marketplace.ui

import android.app.Application
import com.narcissus.marketplace.di.domainModule
import com.narcissus.marketplace.di.viewModelsModule
import com.narcissus.marketplace.ui.cart.di.cartModule
import com.narcissus.marketplace.ui.home.di.homeModule
import com.narcissus.marketplace.ui.product_details.di.productDetailsDestinationModule
import com.narcissus.marketplace.ui.product_details.di.productDetailsModule
import com.narcissus.marketplace.ui.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarketplaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarketplaceApp)
            modules(
                domainModule,
                viewModelsModule,
                homeModule,
                cartModule,
                splashModule,
                productDetailsModule,
            )
        }
    }
}
