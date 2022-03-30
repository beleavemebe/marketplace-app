package com.narcissus.marketplace.apiclient.api.model

import com.google.gson.annotations.SerializedName

data class DepartmentsResponse(
    @SerializedName(SerializedNames.data)
    val data: List<DepartmentResponseData>
)

data class DepartmentResponseData(
    @SerializedName(SerializedNames.departmentId)
    val id: String,

    @SerializedName(SerializedNames.departmentName)
    val name: String,

    @SerializedName(SerializedNames.departmentNumProducts)
    val numberOfProducts: Int,

    @SerializedName(SerializedNames.departmentImageUrl)
    val imageUrl: String,
)
