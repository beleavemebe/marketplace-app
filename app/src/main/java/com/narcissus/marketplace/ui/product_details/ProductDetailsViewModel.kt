package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.usecase.GetProductDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProductDetailsViewModel(
    private val productId: String,
    private val getProductDetails: GetProductDetails
) : ViewModel() {

    val productDetailsFlow: Flow<ProductDetails> = flow {
        val details =
            runCatching {
                getProductDetails(productId).getOrThrow()
            }.getOrNull()
        details?.let { emit(it) }

    }

}
