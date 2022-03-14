package com.narcissus.marketplace.ui.product_details.main_info_recycler_view

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoItem

class ProductDetailsMainInfoAdapter(
    purchaseButtonListener: () -> Unit,
    goToCartButtonListener: () -> Unit,
) : AsyncListDifferDelegationAdapter<ProductMainInfoItem>(
    ProductMainInfoItem.DIFF_CALLBACK,
    ProductMainInfoItem.ProductMainInfoRatingSection.delegate,
    ProductMainInfoItem.ProductMainInfoPurchaseButtonActive.delegate(
        purchaseButtonListener,
    ),
    ProductMainInfoItem.ProductMainInfoPurchaseButtonInactive.delegate(
        goToCartButtonListener,
    ),
)

