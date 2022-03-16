package com.narcissus.marketplace.domain.model

data class Review(
    val reviewId: String,
    val author: String,
    val details: String,
    val rating: Int,
    val reviewAuthorIcon: String
)
