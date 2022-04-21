package com.narcissus.marketplace.ui

import android.app.Application
import com.narcissus.marketplace.di.domainModule
import com.narcissus.marketplace.di.viewModelsModule
import com.narcissus.marketplace.ui.sign_in.di.signInModule
import com.narcissus.marketplace.ui.checkout.di.checkoutModule
import com.narcissus.marketplace.ui.user.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class MarketplaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarketplaceApp)
            workManagerFactory()
            modules(
                domainModule,
                viewModelsModule,
                signInModule,
                checkoutModule,
                userModule,
            )
        }
    }
}
