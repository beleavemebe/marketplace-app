package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.ProductPreview

internal object DummyProducts {
    val previews = listOf(
        ProductPreview("1", "", 1449, "Apple MacBook Pro 13", "", "", 752, "", "", 3, 152),
        ProductPreview("2", "", 1299, "Apple MacBook Air 13", "", "", 1021, "", "", 5, 196),
        ProductPreview("3", "", 2199, "Apple MacBook Pro 16", "", "", 128, "", "", 4, 65),
    )
}
