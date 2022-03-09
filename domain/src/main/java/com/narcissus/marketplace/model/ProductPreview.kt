package com.narcissus.marketplace.model

data class ProductPreview(
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val department: String,
    val type: String,
    val stock: Int,
    val color: String,
    val material: String,
    val rating: Int,
    val sales: Int
)
