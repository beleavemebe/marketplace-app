package com.narcissus.marketplace.ui.home.recycler

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class HomeScreenAdapter(
    onProductClicked: (id: String) -> Unit,
) : AsyncListDifferDelegationAdapter<HomeScreenItem>(
    HomeScreenItem.DIFF_CALLBACK,
    HomeScreenItem.Header.delegate,
    HomeScreenItem.ProductList.delegate(onProductClicked),
    HomeScreenItem.LoadingProductList.delegate,
)
