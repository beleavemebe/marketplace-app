package com.narcissus.marketplace.data.model

data class ProductPreviewBean(
    var id: String = "",
    var icon: String = "",
    var price: Int = 0,
    var name: String = "",
    var department: String = "",
    var type: String = "",
    var stock: Int = 0,
    var color: String = "",
    var material: String = "",
    var rating: Int = 0,
    var sales: Int = 0
)
