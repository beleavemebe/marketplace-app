package com.narcissus.marketplace.ui.product_details.di

import com.narcissus.marketplace.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel { (productId: String) -> ProductDetailsViewModel(productId, get(), get(), get()) }
}
