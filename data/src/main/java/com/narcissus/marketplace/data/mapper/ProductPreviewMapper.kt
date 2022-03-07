package com.narcissus.marketplace.data.mapper

import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.model.ProductPreview

fun ProductEntity.toProductPreview(): ProductPreview {
    return ProductPreview(
        id = id,
        icon = icon,
        price = price,
        name = name,
        category = category,
        type = type,
        stock = stock,
        color = color,
        material = material,
        rating = rating,
        sales = sales
    )
}


