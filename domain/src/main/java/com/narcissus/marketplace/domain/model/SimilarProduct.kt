package com.narcissus.marketplace.domain.model

data class SimilarProduct(
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val category: String,
    val type: String,
    val stock: Int,
    val rating: Int,
)
