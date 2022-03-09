package com.narcissus.marketplace.di

import com.narcissus.marketplace.data.di.dataModule
import com.narcissus.marketplace.usecase.*
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val domainModule = module {
    loadKoinModules(dataModule)

    factory { GetTopRatedProducts(get()) }
    factory { GetTopSalesProducts(get()) }
    factory { GetRandomProducts(get()) }
    factory { GetRecentlyVisitedProducts(get()) }
    factory { GetProductDetails(get()) }

    factory { GetCart(get()) }
    factory { GetCartItemsAmount(get())}
    factory { GetCartCost(get())}
    factory { SetCartItemAmount(get())}
    factory { SetCartItemSelected(get())}
    factory { SelectAllCartItems(get())}
    factory { RemoveSelectedCartItems(get())}
    factory { RemoveFromCart(get())}
}
