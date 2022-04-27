package com.narcissus.marketplace.ui.home.recycler

import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ProductsOfTheDayAdapter(
    onProductClicked: (id: String, cardView: MaterialCardView) -> Unit
) : ListDelegationAdapter<List<ProductOfTheDayItem>>(
    ProductOfTheDayItem.delegate(onProductClicked)
)
