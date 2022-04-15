package com.narcissus.marketplace.ui.search.di

import com.narcissus.marketplace.ui.search.search_history.SearchHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelsModule = module {
    viewModel{ SearchHistoryViewModel(get(),get(),get()) }
}
