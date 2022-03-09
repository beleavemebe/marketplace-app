package com.narcissus.marketplace.ui.product_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.toProductPreview
import com.narcissus.marketplace.usecase.AddToCart
import com.narcissus.marketplace.usecase.GetProductDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productId: String,
    private val getProductDetails: GetProductDetails,
    private val addToCart: AddToCart,
) : ViewModel() {

    val productDetailsFlow: Flow<ProductDetails> = flow {
        val details =
            runCatching {
                getProductDetails(productId).getOrThrow()
            }.getOrNull()

        details?.let { emit(it) }
    }

    fun purchase(product: ProductDetails) {
        viewModelScope.launch {
            addToCart(CartItem(product.toProductPreview(), 1, false))
        }
    }
}
