package com.narcissus.marketplace.ui.products

import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductsAdapter(
    onProductClicked: (id: String, cardView: MaterialCardView) -> Unit
) : AsyncListDifferDelegationAdapter<ProductListItem>(
    ProductListItem.DIFF_CALLBACK,
    ProductListItem.LoadingProduct.delegate(),
    ProductListItem.Product.delegate(onProductClicked),
)
