package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.usecase.AddToCart
import com.narcissus.marketplace.usecase.GetProductDetails
import kotlinx.coroutines.flow.Flow
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

    // TODO: fix absent properties and move elsewhere
    private fun ProductDetails.toProductPreview(): ProductPreview {
        return ProductPreview(
            id,
            icon,
            price,
            name,
            department,
            type,
            stock,
            "lol xd",
            "wtf",
            rating,
            sales
        )
    }
}
