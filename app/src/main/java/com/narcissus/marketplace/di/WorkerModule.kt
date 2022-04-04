package com.narcissus.marketplace.di

import com.narcissus.marketplace.ui.checkout.CheckoutForegroundWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module

val workerModule = module {
    worker { CheckoutForegroundWorker(androidContext(),get()) }
    factory(named("checkout worker result key")) { "checkout result message" }
}
