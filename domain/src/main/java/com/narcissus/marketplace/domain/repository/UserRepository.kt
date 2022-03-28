package com.narcissus.marketplace.domain.repository

import com.narcissus.marketplace.domain.auth.AuthState
import com.narcissus.marketplace.domain.auth.SignInResult
import com.narcissus.marketplace.domain.auth.SignOutResult
import com.narcissus.marketplace.domain.auth.SignUpResult
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.User
import com.narcissus.marketplace.domain.util.ActionResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun addCard(cardNumber: Long, svv: Int, expirationDate: String)

    suspend fun getUserData(): ActionResult<User>
    suspend fun isUserAuthenticated(): Boolean
    suspend fun signInWithEmail(email: String, password: String): SignInResult
    suspend fun signUpWithEmail(fullName: String, email: String, password: String): SignUpResult
    suspend fun signOut(): SignOutResult
    suspend fun signInWithGoogle(idToken: String): SignInResult

    val authStateFlow: Flow<AuthState>

    val recentlyVisitedProducts: Flow<List<ProductPreview>>
    suspend fun writeToVisitedProducts(productPreview: ProductPreview)
}
