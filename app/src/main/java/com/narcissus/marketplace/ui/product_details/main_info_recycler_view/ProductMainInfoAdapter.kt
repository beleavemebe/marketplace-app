package com.narcissus.marketplace.ui.product_details.main_info_recycler_view

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductMainInfoAdapter(
    onPurchaseClicked: () -> Unit,
    onGoToCartClicked: () -> Unit,
) : AsyncListDifferDelegationAdapter<ProductMainInfoItem>(
    ProductMainInfoItem.DIFF_CALLBACK,
    ProductMainInfoItem.RatingSection.delegate(),
    ProductMainInfoItem.ActivePurchaseButton.delegate(onPurchaseClicked),
    ProductMainInfoItem.InactivePurchaseButton.delegate(onGoToCartClicked),
)
