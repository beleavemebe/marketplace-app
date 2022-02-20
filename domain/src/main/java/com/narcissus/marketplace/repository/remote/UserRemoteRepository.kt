package com.narcissus.marketplace.repository.remote

import com.narcissus.marketplace.model.User
import com.narcissus.marketplace.util.ActionResult

interface UserRemoteRepository {
    suspend fun addCard(cardNumber:Long)
    suspend fun getUserData(): ActionResult<User>
    //TODO дописать для авторизации

}