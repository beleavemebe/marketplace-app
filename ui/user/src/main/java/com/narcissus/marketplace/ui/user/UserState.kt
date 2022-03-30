package com.narcissus.marketplace.ui.user

import com.narcissus.marketplace.domain.model.UserProfile

data class UserState(
    val isLoading: Boolean,
    val isUserAuthenticated: Boolean? = null,
    val user: UserProfile? = null,
)
