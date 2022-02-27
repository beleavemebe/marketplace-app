package com.narcissus.marketplace

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.repository.local.UserLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserLocalRepositoryImpl : UserLocalRepository {
    override fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>> {
        return flow {
            emit(DummyProducts.previews)
        }
    }
}
