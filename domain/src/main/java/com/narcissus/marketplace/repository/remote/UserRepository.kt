package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.AuthResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addCard(
        cardNumber: Long,
        svv: Int,
        expirationDate: String
    )

    suspend fun getUserData(): ActionResult<User>
    suspend fun isUserAuthentificated(): Boolean // подумать об анонимной аутентификации
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String): AuthResult
    suspend fun signOut(): AuthResult
    suspend fun signInWithGoogle() // TODO

    // fun isUserLoggedIn(): Flow<Boolean>
    fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>>
    suspend fun writeToVisitedProducts(productPreview: ProductPreview)
}
