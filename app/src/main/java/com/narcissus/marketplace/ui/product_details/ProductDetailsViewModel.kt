package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.model.toProductPreview
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoItem
import com.narcissus.marketplace.ui.product_details.model.ReviewParcelable
import com.narcissus.marketplace.ui.product_details.model.ToolBarData
import com.narcissus.marketplace.usecase.AddToCart
import com.narcissus.marketplace.usecase.GetProductDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
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
) : ViewModel() {

    private val reviewsExpandedStateFlow = MutableStateFlow(false)
    private val purchaseButtonActiveStateFlow = MutableStateFlow(true)

    private val productDetailsDataFlow: Flow<ProductDetails> = flow {
        val details =
            runCatching {
                getProductDetails(productId).getOrThrow()
            }.getOrNull()
        details?.let {
            emit(it)
            reviewsFlow.emit(mapReviews(it.reviews))
        }
    }.shareIn(
        CoroutineScope(Dispatchers.IO),
        replay = 1,
        started = SharingStarted.WhileSubscribed(),
    )


    val productDetailsFlow: SharedFlow<List<ProductDetailsItem>> =
        combine(
            productDetailsDataFlow,
            reviewsExpandedStateFlow,
            purchaseButtonActiveStateFlow,
        ) { details, reviewsState, purchaseActiveState ->
            mapDetails(details, reviewsState, purchaseActiveState)
        }.shareIn(
            CoroutineScope(Dispatchers.IO),
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val productDetailsToolBarFlow: SharedFlow<ToolBarData> =
        productDetailsDataFlow.map { ToolBarData(it.icon, it.name) }.shareIn(
            CoroutineScope(Dispatchers.IO),
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )
    val reviewsFlow: MutableStateFlow<List<ReviewParcelable>> = MutableStateFlow(listOf())


    fun collapseReviewState() {
        viewModelScope.launch {
            reviewsExpandedStateFlow.emit(false)
        }
    }


    fun changeReviewExpandedState() {
        viewModelScope.launch {
            reviewsExpandedStateFlow.emit(!reviewsExpandedStateFlow.value)
        }
    }

    fun purchase() {
        viewModelScope.launch {
            if (purchaseButtonActiveStateFlow.value) {
                addToCart(CartItem(productDetailsDataFlow.first().toProductPreview(), 1, false))
                purchaseButtonActiveStateFlow.emit(!purchaseButtonActiveStateFlow.value)
            }

        }
    }

    private fun mapDetails(
        details: ProductDetails,
        reviewsState: Boolean,
        purchaseActiveState: Boolean,
    ) =
        listOf(
            ProductDetailsItem.Price(details.price),
            ProductDetailsItem.ProductMainInfo(
                listOf(
                    ProductMainInfoItem.ProductMainInfoRatingSection(
                        details.rating,
                        details.sales,
                        details.stock,
                    ),
                    getProductPurchaseButtonState(purchaseActiveState),
                ),

                ),
            ProductDetailsItem.BasicTitle(R.string.about),
            ProductDetailsItem.AboutSingleLine(R.string.type, details.type),
            ProductDetailsItem.AboutSingleLine(R.string.color, details.color),
            ProductDetailsItem.AboutSingleLine(R.string.material, details.material),
            ProductDetailsItem.AboutMultipleLine(R.string.description, details.description),
            ProductDetailsItem.Divider(),
            ProductDetailsItem.ButtonTitle(R.string.reviews, R.string.all_reviews),
            ProductDetailsItem.ReviewsPreview(details.reviews[0], reviewsState),
            ProductDetailsItem.Divider(),
            ProductDetailsItem.BasicTitle(R.string.similar_products),
            ProductDetailsItem.SimilarProducts(details.similarProducts),
        )

    private fun mapReviews(reviews: List<Review>) = reviews.map { review ->
        ReviewParcelable(
            review.reviewId,
            review.author,
            review.details,
            review.rating,
            review.reviewAuthorIcon,
        )
    }

    private fun getProductPurchaseButtonState(purchaseActiveState: Boolean): ProductMainInfoItem {
        return if (purchaseActiveState)
            ProductMainInfoItem.ProductMainInfoPurchaseButtonActive(R.string.purchase)
        else
            ProductMainInfoItem.ProductMainInfoPurchaseButtonInactive(R.string.go_to_cart)
    }
}
