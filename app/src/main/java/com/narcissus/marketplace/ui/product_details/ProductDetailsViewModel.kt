package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.usecase.GetProductDetails
import com.narcissus.marketplace.util.ActionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    companion object{
        const val CONSTRICT_REVIEWS_LIST_SIZE = 2
    }
    val getProductDetails = ServiceLocator.getProductDetails
    val productDetailsFlow = flow {
        val result = getProductDetails("") as ActionResult.SuccessResult
        emit(result.data)
    }
    val isReviewsListExpandedFlow = MutableStateFlow(false)
    val productReviewsFlow =
        isReviewsListExpandedFlow.combine(productDetailsFlow) { isReviewsListExpanded, productDetails ->
        if (isReviewsListExpanded) productDetails.reviews
        else productDetails.reviews.subList(0, CONSTRICT_REVIEWS_LIST_SIZE)
        }

    fun changeReviewsListState() {
        CoroutineScope(Dispatchers.Main).launch {
            isReviewsListExpandedFlow.emit(!isReviewsListExpandedFlow.value)
        }
    }

}
