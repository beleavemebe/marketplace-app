package com.narcissus.marketplace.ui.product_details.model

import com.narcissus.marketplace.domain.model.SimilarProduct

data class PresentationSimilarProduct(val similarProduct: SimilarProduct, val isButtonAddToCartActive: Boolean)
