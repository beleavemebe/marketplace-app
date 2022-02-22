package com.narcissus.marketplace.repository.remote


import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.AuthResult

interface UserRemoteRepository {
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
    suspend fun signInWithGoogle()//TODO
}