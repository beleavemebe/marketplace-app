package com.narcissus.marketplace.ui.home.recycler

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ProductsOfTheDayAdapter(
    onProductClicked: (id: String) -> Unit
) : ListDelegationAdapter<List<ProductOfTheDayItem>>(
    ProductOfTheDayItem.delegate(onProductClicked)
)
