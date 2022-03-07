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

    val productDetailsFlow: Flow<ProductDetailsScreenData> = flow {
    val productDetailsFlow: Flow<ProductDetails> = flow {
        val details =
            runCatching {
                getProductDetails(productId).getOrThrow()
            }.getOrNull()

        details?.let { emit(it.toProductDetailsScreenData()) }
    }

    fun purchase(product: ProductDetails) {
        viewModelScope.launch {
            addToCart(CartItem(product.toProductPreview(), 1, false))
        }
    }
}

// TODO: fix absent properties and move elsewhere
private fun ProductDetails.toProductPreview(): ProductPreview {
    return ProductPreview(id, icon, price, name, department, "lol xdd", stock, "lol xd", "wtf", rating, sales)

    private fun ProductDetails.toProductDetailsScreenData(): ProductDetailsScreenData =
        ProductDetailsScreenData(
            id,
            icon,
            price,
            name,
            type,
            department,
            stock,
            rating,
            sales,
            listOf(
                DetailsAbout.Type(type),
                DetailsAbout.Color(color),
                DetailsAbout.Material(material),
                DetailsAbout.Description(description)
            ),
            reviews,
            similarProducts
        )
}
