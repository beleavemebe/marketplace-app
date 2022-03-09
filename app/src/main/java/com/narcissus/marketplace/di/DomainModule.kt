package com.narcissus.marketplace.di

import com.narcissus.marketplace.data.di.dataModule
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import com.narcissus.marketplace.usecase.GetProductDetails
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.SetCartItemAmount
import com.narcissus.marketplace.usecase.SetCartItemSelected
import com.narcissus.marketplace.usecase.SelectAllCartItems
import com.narcissus.marketplace.usecase.RemoveFromCart
import com.narcissus.marketplace.usecase.RemoveSelectedCartItems

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
