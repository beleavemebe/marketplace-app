package com.narcissus.marketplace.ui.home.recycler

data class ProductOfTheDay(
    val id: String,
    val imageUrl: String,
    val name: String,
    val oldPrice: Int,
    val newPrice: Int,
    val percentOff: Int,
)
