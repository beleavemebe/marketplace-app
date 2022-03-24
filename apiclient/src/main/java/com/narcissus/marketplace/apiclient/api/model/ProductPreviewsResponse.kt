package com.narcissus.marketplace.apiclient.api.model

import com.google.gson.annotations.SerializedName

data class ProductPreviewsResponse(
    @SerializedName(SerializedNames.data)
    val data: List<ProductPreviewResponseData>
)

data class ProductPreviewResponseData(
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
    @SerializedName(SerializedNames.productDepartment)
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
    val sales: Int
)
