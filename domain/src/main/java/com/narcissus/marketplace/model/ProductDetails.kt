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

fun ProductDetails.toProductPreview(): ProductPreview {
    return ProductPreview(
        id = id,
        icon = icon,
        price = price,
        name = name,
        category = department,
        type = "lol xdd",
        stock = stock,
        color = "lol xd",
        material = "wtf",
        rating = rating,
        sales = sales
    )
}

