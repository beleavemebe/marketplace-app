package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.apiclient.api.model.ApiStatusResponse
import com.narcissus.marketplace.domain.model.ApiStatus

fun ApiStatusResponse.toApiStatus() =
    ApiStatus(isAvailable)
