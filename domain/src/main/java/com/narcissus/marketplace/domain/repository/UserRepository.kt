package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.User
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addCard(
        cardNumber: Long,
        svv: Int,
        expirationDate: String,
    )

    suspend fun getUserData(): ActionResult<User>
    suspend fun isUserAuthenticated(): Boolean // подумать об анонимной аутентификации
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(fullName: String, email: String, password: String): AuthResult
    suspend fun signOut(): AuthResult
    suspend fun signInWithGoogle(idToken: String): AuthResult

    // fun isUserLoggedIn(): Flow<Boolean>
    fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>>
    suspend fun writeToVisitedProducts(productPreview: ProductPreview)
}
