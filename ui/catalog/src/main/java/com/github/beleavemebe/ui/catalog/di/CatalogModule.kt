package com.github.beleavemebe.ui.catalog.di

import com.github.beleavemebe.ui.catalog.CatalogViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catalogModule = module {
    viewModel { CatalogViewModel(get()) }
}
