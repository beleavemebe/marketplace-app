package com.narcissus.marketplace.domain.auth

import com.narcissus.marketplace.domain.model.UserProfile

sealed class AuthState {
    object Unknown : AuthState()
    object NotAuthenticated : AuthState()
    data class Authenticated(val user: UserProfile?) : AuthState()
}
