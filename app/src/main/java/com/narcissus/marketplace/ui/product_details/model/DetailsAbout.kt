package com.narcissus.marketplace.ui.product_details.model

sealed class DetailsAbout(val data: String) {
    class Type(data: String) : DetailsAbout(data)
    class Color(data: String) : DetailsAbout(data)
    class Material(data: String) : DetailsAbout(data)
    class Description(data: String) : DetailsAbout(data)
}