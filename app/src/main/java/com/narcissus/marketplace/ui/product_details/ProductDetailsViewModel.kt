package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.usecase.GetProductDetails
import com.narcissus.marketplace.util.ActionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productId: String,
    private val getProductDetails: GetProductDetails
) : ViewModel() {
    companion object {
        const val COLLAPSED_REVIEWS_LIST_SIZE = 1
    }

    val productDetailsFlow = flow {
        val result = getProductDetails(productId) as ActionResult.SuccessResult
        emit(result.data)
    }

    private val _isReviewsListExpandedFlow = MutableStateFlow(false)
    val isReviewListExpandedFlow = _isReviewsListExpandedFlow.asStateFlow()

    val reviewsFlow: Flow<List<Review>> =
        isReviewListExpandedFlow
            .combine(productDetailsFlow) { isReviewsListExpanded, productDetails ->
                if (isReviewsListExpanded) productDetails.reviews
                else productDetails.reviews.subList(0, COLLAPSED_REVIEWS_LIST_SIZE)
            }

    fun changeReviewsListState() {
        viewModelScope.launch {
            _isReviewsListExpandedFlow.emit(!isReviewListExpandedFlow.value)
        }
    }
}
