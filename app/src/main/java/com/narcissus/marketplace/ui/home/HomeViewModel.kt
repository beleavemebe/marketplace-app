package com.narcissus.marketplace.ui.home

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import com.narcissus.marketplace.util.ActionResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class HomeViewModel(
    private val getTopRatedProducts: GetTopRatedProducts = ServiceLocator.getTopRatedProducts,
    private val getTopSalesProducts: GetTopSalesProducts = ServiceLocator.getTopSalesProducts,
    private val getRandomProducts: GetRandomProducts = ServiceLocator.getRandomProducts,
    private val getRecentlyVisitedProducts: GetRecentlyVisitedProducts = ServiceLocator.getRecentlyVisitedProducts,
) : ViewModel() {
    val topRatedFlow = flow {
        delay(500L)
        val result = getTopRatedProducts() as ActionResult.SuccessResult
        emit(result.data)
    }

    val topSalesFlow = flow {
        delay(1000L)
        val result = getTopSalesProducts() as ActionResult.SuccessResult
        emit(result.data)
    }

    val randomFlow = flow {
        delay(1500L)
        val result = getRandomProducts() as ActionResult.SuccessResult
        emit(result)
    }

    val recentlyVisitedFlow = getRecentlyVisitedProducts()
}
