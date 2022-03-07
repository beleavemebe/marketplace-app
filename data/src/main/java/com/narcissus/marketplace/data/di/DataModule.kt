package com.narcissus.marketplace.data.di

import com.narcissus.marketplace.data.CartLocalRepositoryImpl
import com.narcissus.marketplace.apiclient.di.apiClientModule
import com.narcissus.marketplace.data.DepartmentsRepositoryImpl
import com.narcissus.marketplace.data.OrderRepositoryImpl
import com.narcissus.marketplace.data.ProductsDetailsRepositoryImpl
import com.narcissus.marketplace.data.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.data.UserLocalRepositoryImpl
import com.narcissus.marketplace.data.UserRemoteRepositoryImpl
import com.narcissus.marketplace.repository.local.CartLocalRepository
import com.narcissus.marketplace.repository.local.UserLocalRepository
import com.narcissus.marketplace.repository.remote.DepartmentsRepository
import com.narcissus.marketplace.repository.remote.OrderRepository
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.repository.remote.ProductsPreviewRepository
import com.narcissus.marketplace.repository.remote.UserRemoteRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val dataModule = module {
    loadKoinModules(apiClientModule)

    single<CartLocalRepository> { CartLocalRepositoryImpl() }
    single<DepartmentsRepository> { DepartmentsRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl() }
    single<ProductsDetailsRepository> { ProductsDetailsRepositoryImpl(apiService = get()) }

    single<ProductsPreviewRepository> {
        ProductsPreviewRepositoryImpl(apiService = get())
    }

    single<UserLocalRepository> { UserLocalRepositoryImpl() }
    single<UserRemoteRepository> { UserRemoteRepositoryImpl() }
}
