package com.narcissus.marketplace.domain.model

data class UserProfile(
    val id: String,
    val name: String?,
    val email: String,
    val iconUrl: String?
)
