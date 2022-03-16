package com.narcissus.marketplace.ui.product_details

import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductDetailsAdapter(
    purchaseClicked: () -> Unit,
    goToCartClicked: () -> Unit,
    allReviewsClicked: () -> Unit,
    reviewTextClicked: () -> Unit,
    similarProductClicked: (productId: String, cardView: MaterialCardView) -> Unit,
    addSimilarProductToCartClicked: (ProductId: String) -> Unit,
) : AsyncListDifferDelegationAdapter<ProductDetailsItem>(
    ProductDetailsItem.DIFF_CALLBACK,
    ProductDetailsItem.Price.delegate,
    ProductDetailsItem.ProductMainInfo.delegate(purchaseClicked, goToCartClicked),
    ProductDetailsItem.ProductMainInfoPlaceHolder.delegate,
    ProductDetailsItem.BasicTitle.delegate,
    ProductDetailsItem.ProductDetailsPlaceHolder.delegate,
    ProductDetailsItem.AboutSingleLine.delegate,
    ProductDetailsItem.AboutMultipleLine.delegate,
    ProductDetailsItem.ButtonTitle.delegate(allReviewsClicked),
    ProductDetailsItem.ReviewsPreview.delegate(reviewTextClicked),
    ProductDetailsItem.SimilarProducts.delegate(
        similarProductClicked,
        addSimilarProductToCartClicked,
    ),
    ProductDetailsItem.Divider.delegate,
)
