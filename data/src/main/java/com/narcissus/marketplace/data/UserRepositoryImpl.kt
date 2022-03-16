package com.narcissus.marketplace.data

import com.narcissus.marketplace.data.mapper.toProductPreview
import com.narcissus.marketplace.data.persistence.database.ProductDao
import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.repository.UserRepository
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class UserRepositoryImpl(
    private val productsDao: ProductDao,
) : UserRepository {
    override fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>> {
        return productsDao.getProducts().map { entities ->
            entities.map(ProductEntity::toProductPreview)
        }
    }

    override suspend fun writeToVisitedProducts(productPreview: ProductPreview) {
        productsDao.insertProduct(productPreview.toProductEntity())
    }

    override suspend fun addCard(cardNumber: Long, svv: Int, expirationDate: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): ActionResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun isUserAuthentificated(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle() {
        TODO("Not yet implemented")
    }
}

private fun ProductPreview.toProductEntity(): ProductEntity {
    return ProductEntity(
        id,
        icon,
        price,
        name,
        department,
        type,
        stock,
        color,
        material,
        rating,
        sales
    )
}
