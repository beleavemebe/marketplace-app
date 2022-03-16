package com.narcissus.marketplace.domain.model

data class ProductDetails(
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val department: String,
    val type: String,
    val stock: Int,
    val color: String,
    val material: String,
    val description: String,
    val rating: Int,
    val sales: Int,
    val reviews: List<Review>,
    val similarProducts: List<SimilarProduct>
)

fun ProductDetails.toProductPreview(): ProductPreview {
    return ProductPreview(
        id,
        icon,
        price,
        name,
        department,
        type,
        stock,
        color,
        material,
        rating,
        sales
    )
}
