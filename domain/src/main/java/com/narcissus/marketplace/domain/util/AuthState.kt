package com.narcissus.marketplace.domain.util

import com.narcissus.marketplace.domain.model.UserProfile

data class AuthState(
    val user: UserProfile?
)
