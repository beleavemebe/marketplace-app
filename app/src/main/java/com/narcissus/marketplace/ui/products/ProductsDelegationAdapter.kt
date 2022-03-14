package com.narcissus.marketplace.ui.products

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductsDelegationAdapter(
    onProductClicked: (id: String) -> Unit
) : AsyncListDifferDelegationAdapter<ProductListItem>(
    ProductListItem.DIFF_CALLBACK,
    ProductListItem.LoadingProduct.delegate,
    ProductListItem.Product.delegate(onProductClicked),
)
