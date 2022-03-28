package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.apiclient.api.model.DepartmentResponseData
import com.narcissus.marketplace.domain.model.Department

fun DepartmentResponseData.toDepartment() =
    Department(
        id, name, numberOfProducts, imageUrl,
    )
