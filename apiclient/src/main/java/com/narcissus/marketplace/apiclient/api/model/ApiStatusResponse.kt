package com.narcissus.marketplace.apiclient.api.model

import com.google.gson.annotations.SerializedName

data class ApiStatusResponse(
    @SerializedName(SerializedNames.isAvailable)
    val isAvailable: Boolean,
)
