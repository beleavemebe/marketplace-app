package com.narcissus.marketplace.domain.model

data class Order(
    val id: Int,
    val data: List<ProductPreview>
)
