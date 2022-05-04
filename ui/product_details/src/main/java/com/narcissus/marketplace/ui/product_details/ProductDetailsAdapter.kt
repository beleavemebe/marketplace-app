package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ProductDetailsAdapter(
    purchaseClicked: () -> Unit,
    goToCartClicked: () -> Unit,
    allReviewsClicked: () -> Unit,
    lifecycle: Lifecycle,
    scope: LifecycleCoroutineScope,
    onSimilarProductClicked: (productId: String, cardView: MaterialCardView) -> Unit,
    onAddSimilarProductToCartClicked: (ProductId: String) -> Unit,
) : AsyncListDifferDelegationAdapter<ProductDetailsItem>(
    ProductDetailsItem.DIFF_CALLBACK,
    ProductDetailsItem.Divider.delegate(),
    ProductDetailsItem.Price.delegate(),
    ProductDetailsItem.ProductMainInfo.delegate(purchaseClicked, goToCartClicked),
    ProductDetailsItem.LoadingMainProductInfo.delegate(),
    ProductDetailsItem.BasicTitle.delegate(),
    ProductDetailsItem.LoadingProductDetails.delegate(),
    ProductDetailsItem.AboutSingleLine.delegate(),
    ProductDetailsItem.AboutMultipleLine.delegate(),
    ProductDetailsItem.ButtonTitle.delegate(allReviewsClicked),
    ProductDetailsItem.ReviewsPreview.delegate(lifecycle, scope),
    ProductDetailsItem.SimilarProducts.delegate(
        onSimilarProductClicked, onAddSimilarProductToCartClicked
    ),
)
