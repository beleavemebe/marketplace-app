package com.narcissus.marketplace.ui

import android.app.Application
import com.narcissus.marketplace.data.di.dataModule
import org.koin.core.context.startKoin

class MarketplaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataModule)
        }
    }
}
