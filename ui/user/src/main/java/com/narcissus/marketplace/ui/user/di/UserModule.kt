package com.narcissus.marketplace.ui.user.di

import com.narcissus.marketplace.ui.user.UserViewModel
import com.narcissus.marketplace.ui.user.orders.OrdersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    viewModel { UserViewModel(get(), get()) }
    viewModel { OrdersViewModel(get()) }
}
