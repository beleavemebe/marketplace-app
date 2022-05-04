package com.narcissus.marketplace.ui.catalog.di

import com.narcissus.marketplace.ui.catalog.CatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    viewModel { CatalogViewModel(get()) }
}
