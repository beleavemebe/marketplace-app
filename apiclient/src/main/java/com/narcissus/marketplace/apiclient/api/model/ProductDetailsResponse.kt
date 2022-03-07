package com.narcissus.marketplace.apiclient.api.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName(SerializedNames.data)
    val data: ProductDetailsResponseData
)

data class ProductDetailsResponseData(
    @SerializedName(SerializedNames.id)
    val id: String,
    @SerializedName(SerializedNames.icon)
    val productImg: String,
    @SerializedName(SerializedNames.productName)
    val name: String,
    @SerializedName(SerializedNames.price)
    val price: Int,
    @SerializedName(SerializedNames.type)
    val type: String,
    @SerializedName(SerializedNames.departmentName)
    val departmentName: String,
    @SerializedName(SerializedNames.stock)
    val stock: Int,
    @SerializedName(SerializedNames.color)
    val color: String,
    @SerializedName(SerializedNames.material)
    val material: String,
    @SerializedName(SerializedNames.productRating)
    val rating: Int,
    @SerializedName(SerializedNames.sales)
    val sales: Int,
    @SerializedName(SerializedNames.productDescription)
    val description: String,
    @SerializedName(SerializedNames.productReviews)
    val reviewsList: List<ReviewResponseData>,
    @SerializedName(SerializedNames.similarProducts)
    val similarProductsList: List<SimilarProductsResponseData>,
)

class ReviewResponseData(
    @SerializedName(SerializedNames.id)
    val reviewId: String,
    @SerializedName(SerializedNames.reviewProductId)
    val productId: String,
    @SerializedName(SerializedNames.reviewAuthor)
    val author: String,
    @SerializedName(SerializedNames.reviewDetails)
    val details: String,
    @SerializedName(SerializedNames.reviewRating)
    val rating: Int,
    @SerializedName(SerializedNames.reviewAvatar)
    val reviewAuthorIcon: String
)
class SimilarProductsResponseData(
    @SerializedName(SerializedNames.id)
    val id: String,
    @SerializedName(SerializedNames.icon)
    val icon: String,
    @SerializedName(SerializedNames.productName)
    val name: String,
    @SerializedName(SerializedNames.price)
    val price: Int,
    @SerializedName(SerializedNames.type)
    val type: String,
    @SerializedName(SerializedNames.departmentName)
    val departmentName: String,
    @SerializedName(SerializedNames.stock)
    val stock: Int,
    @SerializedName(SerializedNames.productRating)
    val rating: Int,
    @SerializedName(SerializedNames.sales)
    val sales: Int
)
