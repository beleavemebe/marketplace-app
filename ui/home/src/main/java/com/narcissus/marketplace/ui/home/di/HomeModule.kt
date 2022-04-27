package com.narcissus.marketplace.ui.home.di

import com.narcissus.marketplace.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get(), get(), get(), get(), get()) }
}
