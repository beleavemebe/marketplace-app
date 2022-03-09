package com.narcissus.marketplace.di

import com.narcissus.marketplace.ui.cart.CartViewModel
import com.narcissus.marketplace.ui.home.HomeViewModel
import com.narcissus.marketplace.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { (productId: String) -> ProductDetailsViewModel(productId, get()) }
    viewModel { CartViewModel(get(),get(),get(),get(),get(),get(),get(),get())}
}
