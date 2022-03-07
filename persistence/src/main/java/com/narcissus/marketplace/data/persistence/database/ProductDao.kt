package com.narcissus.marketplace.data.persistence.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.narcissus.marketplace.data.persistence.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product ORDER BY created DESC")
    fun getProducts(): Flow<List<ProductEntity>>

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)
}
