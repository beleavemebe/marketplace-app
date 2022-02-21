package com.narcissus.marketplace.repository.remote


import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.util.ActionResult

interface UserRemoteRepository {
    suspend fun addCard(cardNumber: Long)
    suspend fun getUserData(): ActionResult<User>
    suspend fun isUserAuthentificated(): Boolean // подумать об анонимной аутентификации
    suspend fun signInWithEmail(email: String, password: String): ActionResult<Boolean>
    suspend fun signUpWithEmail(email: String, password: String): ActionResult<Boolean>
    suspend fun signOut(): ActionResult<Boolean>
    suspend fun signInWithGoogle()//TODO
}