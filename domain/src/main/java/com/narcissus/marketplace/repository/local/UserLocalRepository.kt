package com.narcissus.marketplace.repository.local

import com.narcissus.marketplace.model.ProductPreview
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    //fun isUserLoggedIn(): Flow<Boolean>
    fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>>
}