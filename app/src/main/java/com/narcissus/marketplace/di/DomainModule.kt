package com.narcissus.marketplace.di

import com.narcissus.marketplace.data.di.dataModule
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val domainModule = module {
    loadKoinModules(dataModule)

    factory { GetTopRatedProducts(get()) }
    factory { GetTopSalesProducts(get()) }
    factory { GetRandomProducts(get()) }
    factory { GetRecentlyVisitedProducts(get()) }
}
