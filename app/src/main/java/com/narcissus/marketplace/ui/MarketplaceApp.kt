package com.narcissus.marketplace.ui

import android.app.Application
import com.narcissus.marketplace.di.domainModule
import com.narcissus.marketplace.di.viewModelsModule
import org.koin.core.context.startKoin

class MarketplaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(domainModule, viewModelsModule)
        }
    }
}
