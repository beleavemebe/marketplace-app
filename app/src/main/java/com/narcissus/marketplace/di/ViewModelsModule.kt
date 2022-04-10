package com.narcissus.marketplace.di

import com.narcissus.marketplace.ui.cart.CartViewModel
import com.narcissus.marketplace.ui.catalog.CatalogViewModel
import com.narcissus.marketplace.ui.checkout.CheckoutViewModel
import com.narcissus.marketplace.ui.home.HomeViewModel
import com.narcissus.marketplace.ui.product_details.ProductDetailsViewModel
import com.narcissus.marketplace.ui.sign_in.SignInViewModel
import com.narcissus.marketplace.ui.sign_up.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { (productId: String) -> ProductDetailsViewModel(productId, get(), get()) }
    viewModel { CatalogViewModel(get()) }
    viewModel { CartViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { CheckoutViewModel(get(), get(), get()) }
}
