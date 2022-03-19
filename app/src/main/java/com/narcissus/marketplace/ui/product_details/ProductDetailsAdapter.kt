package com.narcissus.marketplace.ui.product_details

import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ProductDetailsAdapter(
    purchaseClicked: () -> Unit,
    goToCartClicked: () -> Unit,
    allReviewsClicked: () -> Unit,
    onReviewClicked: () -> Unit,
    isReviewExpanded: Flow<Boolean>,
    scope: CoroutineScope,
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
    ProductDetailsItem.ReviewsPreview.delegate(onReviewClicked, isReviewExpanded, scope),
    ProductDetailsItem.SimilarProducts.delegate(
        onSimilarProductClicked, onAddSimilarProductToCartClicked
    ),
)
