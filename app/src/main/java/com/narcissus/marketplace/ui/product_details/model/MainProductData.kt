package com.narcissus.marketplace.ui.product_details.model

import com.narcissus.marketplace.model.ProductPreview

data class MainProductData(
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val department: String,
    val stock: Int,
    val sales: Int,
    val rating: Int,
    val type: String,
    val color: String,
    val material: String,
    val description: String,
    val similarProducts:List<ProductPreview>
)


