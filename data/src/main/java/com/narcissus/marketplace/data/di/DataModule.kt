package com.narcissus.marketplace.data.di

import com.narcissus.marketplace.apiclient.di.apiClientModule
import com.narcissus.marketplace.data.CartRepositoryImpl
import com.narcissus.marketplace.data.DepartmentsRepositoryImpl
import com.narcissus.marketplace.data.OrderRepositoryImpl
import com.narcissus.marketplace.data.ProductsDetailsRepositoryImpl
import com.narcissus.marketplace.data.ProductsPreviewRepositoryImpl
import com.narcissus.marketplace.data.UserRepositoryImpl
import com.narcissus.marketplace.data.persistence.di.persistenceModule
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.repository.DepartmentsRepository
import com.narcissus.marketplace.domain.repository.OrderRepository
import com.narcissus.marketplace.domain.repository.ProductsDetailsRepository
import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository
import com.narcissus.marketplace.domain.repository.UserRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val dataModule = module {
    loadKoinModules(listOf(apiClientModule, persistenceModule))

    single<CartRepository> { CartRepositoryImpl() }
    single<DepartmentsRepository> { DepartmentsRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl() }

    single<ProductsDetailsRepository> {
        ProductsDetailsRepositoryImpl(apiService = get())
    }

    single<ProductsPreviewRepository> {
        ProductsPreviewRepositoryImpl(apiService = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(productsDao = get())
    }
}
