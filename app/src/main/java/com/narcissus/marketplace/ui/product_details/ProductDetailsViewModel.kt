package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.Review
import com.narcissus.marketplace.domain.usecase.AddToCart
import com.narcissus.marketplace.domain.usecase.GetProductDetails
import com.narcissus.marketplace.domain.usecase.GetSimilarProducts
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoItem
import com.narcissus.marketplace.ui.product_details.model.ParcelableReview
import com.narcissus.marketplace.ui.product_details.model.ToolbarData
import com.narcissus.marketplace.ui.product_details.model.toParcelableReview
import com.narcissus.marketplace.ui.products.ProductListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productId: String,
    private val getProductDetails: GetProductDetails,
    private val addToCart: AddToCart,
    private val getSimilarProducts: GetSimilarProducts,
) : ViewModel() {

    private val isPurchaseButtonActiveStateFlow = MutableStateFlow(true)

    private val productDetailsFlow: Flow<ProductDetails> =
        flow {
            val details = runCatching {
                getProductDetails(productId)
            }.getOrNull()

            if (details != null) {
                emit(details)

                val parcelableReviews = details.reviews.map(Review::toParcelableReview)
                _reviewsFlow.emit(parcelableReviews)
            }
        }.shareIn(
            viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    private val similarProductsFlow: Flow<List<ProductListItem>> = flow {
        val placeholders = Array(5) { ProductListItem.LoadingProduct() }
        emit(placeholders.toList())
        val similarProducts = getSimilarProducts(productId)
        val items = similarProducts.map { ProductListItem.Product(it) }
        emit(items)
    }

    val contentFlow: SharedFlow<List<ProductDetailsItem>> =
        combine(
            productDetailsFlow,
            isPurchaseButtonActiveStateFlow,
            similarProductsFlow,
        ) { details, purchaseActiveState, similarProducts ->
            assembleContent(details, purchaseActiveState, similarProducts)
        }.shareIn(
            viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val productDetailsToolbarFlow: SharedFlow<ToolbarData> =
        productDetailsFlow.map {
            ToolbarData(it.icon, it.name)
        }.shareIn(
            viewModelScope,
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    private val _reviewsFlow = MutableStateFlow(listOf<ParcelableReview>())
    val reviewsFlow = _reviewsFlow.asStateFlow()

    fun purchase() {
        viewModelScope.launch {
            if (isPurchaseButtonActiveStateFlow.value) {
                val product = productDetailsFlow.first()
                addToCart(product)
                isPurchaseButtonActiveStateFlow.emit(false)
            }
        }
    }

    private fun assembleContent(
        details: ProductDetails,
        purchaseActiveState: Boolean,
        similarProducts: List<ProductListItem>,
    ): List<ProductDetailsItem> {
        return listOf(
            ProductDetailsItem.Price(details.price),
            ProductDetailsItem.ProductMainInfo(
                listOf(
                    ProductMainInfoItem.RatingSection(details.rating, details.sales, details.stock),
                    getProductPurchaseButtonState(purchaseActiveState),
                ),
            ),
            ProductDetailsItem.BasicTitle(R.string.about),
            ProductDetailsItem.AboutMultipleLine(R.string.description, details.description),
            ProductDetailsItem.AboutSingleLine(R.string.type, details.type),
            ProductDetailsItem.AboutSingleLine(R.string.color, details.color),
            ProductDetailsItem.AboutSingleLine(R.string.material, details.material),
            ProductDetailsItem.Divider(),
            ProductDetailsItem.ButtonTitle(R.string.reviews, R.string.all_reviews),
            ProductDetailsItem.ReviewsPreview(details.reviews[0]),
            ProductDetailsItem.Divider(),
            ProductDetailsItem.BasicTitle(R.string.similar_products),
            ProductDetailsItem.SimilarProducts(similarProducts),
        )
    }

    private fun getProductPurchaseButtonState(purchaseActiveState: Boolean): ProductMainInfoItem {
        return if (purchaseActiveState) {
            ProductMainInfoItem.ActivePurchaseButton(R.string.purchase)
        } else {
            ProductMainInfoItem.InactivePurchaseButton(R.string.go_to_cart)
        }
    }
}
