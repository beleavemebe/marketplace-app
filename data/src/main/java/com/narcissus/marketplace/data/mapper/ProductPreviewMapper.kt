package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.domain.model.ProductPreview

fun ProductEntity.toProductPreview(): ProductPreview {
    return ProductPreview(
        id = id,
        icon = icon,
        price = price,
        name = name,
        department = department,
        type = type,
        stock = stock,
        color = color,
        material = material,
        rating = rating,
        sales = sales
    )
}
