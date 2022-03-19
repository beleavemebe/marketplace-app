package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.Review
import com.narcissus.marketplace.ui.product_details.model.PresentationSimilarProduct
import com.narcissus.marketplace.ui.product_details.model.ReviewParcelable
import com.narcissus.marketplace.ui.product_details.model.ToolbarData
import com.narcissus.marketplace.domain.usecase.AddToCart
import com.narcissus.marketplace.domain.usecase.GetProductDetails
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoItem
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
import kotlin.random.Random

class ProductDetailsViewModel(
    private val productId: String,
    private val getProductDetails: GetProductDetails,
    private val addToCart: AddToCart,
) : ViewModel() {

    private val reviewsExpandedStateFlow = MutableStateFlow(false)
    private val isPurchaseButtonActiveStateFlow = MutableStateFlow(true)

    private val productDetailsFlow: Flow<ProductDetails> =
        flow {
            val details = runCatching {
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

    val contentFlow: SharedFlow<List<ProductDetailsItem>> =
        combine(
            productDetailsFlow,
            reviewsExpandedStateFlow,
            isPurchaseButtonActiveStateFlow,
        ) { details, reviewsState, purchaseActiveState ->
            assembleContent(details, reviewsState, purchaseActiveState)
        }.shareIn(
            CoroutineScope(Dispatchers.IO),
            replay = 1,
            started = SharingStarted.WhileSubscribed(),
        )

    val productDetailsToolbarFlow: SharedFlow<ToolbarData> =
        productDetailsFlow.map {
            ToolbarData(it.icon, it.name)
        }.shareIn(
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
            if (isPurchaseButtonActiveStateFlow.value) {
                val product = productDetailsFlow.first()
                addToCart(product)
                isPurchaseButtonActiveStateFlow.emit(false)
            }
        }
    }

    private fun assembleContent(
        details: ProductDetails,
        reviewsState: Boolean,
        purchaseActiveState: Boolean,
    ) =
        listOf(
            ProductDetailsItem.Price(details.price),
            ProductDetailsItem.ProductMainInfo(
                listOf(
                    ProductMainInfoItem.RatingSection(details.rating, details.sales, details.stock),
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
            ProductDetailsItem.SimilarProducts(
                details.similarProducts.map { product ->
                    PresentationSimilarProduct(
                        product,
                        Random.Default.nextBoolean(), // todo: ПЕРЕДЕЛАТЬ С КОРЗИНОЙ
                    )
                },
            ),
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
        return if (purchaseActiveState) {
            ProductMainInfoItem.ActivePurchaseButton(R.string.purchase)
        } else {
            ProductMainInfoItem.InactivePurchaseButton(R.string.go_to_cart)
        }
    }
}
