package com.narcissus.marketplace

import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.repository.remote.UserRemoteRepository
import com.narcissus.marketplace.util.ActionResult
import com.narcissus.marketplace.util.AuthResult

class UserRemoteRepositoryImpl : UserRemoteRepository {
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
