package com.narcissus.marketplace.model

data class Review(
    val reviewId: String,
    val author: String,
    val details: String,
    val rating: Int
)
