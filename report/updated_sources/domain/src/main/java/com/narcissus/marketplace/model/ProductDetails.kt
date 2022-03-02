package com.narcissus.marketplace.model

data class ProductDetails(
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val department: String,
    val stock: Int,
    val aboutList: List<DetailsAbout>,
    val rating: Int,
    val sales: Int,
    val reviews: List<Review>,
    val similarProducts: List<ProductPreview>
)
