package com.narcissus.marketplace.ui.product_details.model

import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.model.SimilarProduct

data class ProductDetailsScreenData (
    val id: String,
    val icon: String,
    val price: Int,
    val name: String,
    val type:String,
    val department: String,
    val stock: Int,
    val rating: Int,
    val sales: Int,
    val about:List<DetailsAbout>,
    val reviews: List<Review>,
    val similarProducts: List<SimilarProduct>
        )

sealed class DetailsAbout(val data: String) {
    class Type(data: String) : DetailsAbout(data)
    class Color(data: String) : DetailsAbout(data)
    class Material(data: String) : DetailsAbout(data)
    class Description(data: String) : DetailsAbout(data)
}
