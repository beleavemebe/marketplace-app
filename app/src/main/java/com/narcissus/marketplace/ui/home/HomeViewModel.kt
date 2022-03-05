package com.narcissus.marketplace.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.R
import com.narcissus.marketplace.di.ServiceLocator
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class HomeViewModel(
    private val getTopRatedProducts: GetTopRatedProducts,
    private val getTopSalesProducts: GetTopSalesProducts,
    private val getRandomProducts: GetRandomProducts,
    private val getRecentlyVisitedProducts: GetRecentlyVisitedProducts,
) : ViewModel() {
    private val topRatedFlow = productListFlow {
        getTopRatedProducts().getOrThrow()
    }

    private val topSalesFlow = productListFlow {
        getTopSalesProducts().getOrThrow()
    }

    private val randomFlow = productListFlow {
        getRandomProducts().getOrThrow()
    }

    private val recentlyVisitedFlow = getRecentlyVisitedProducts().map { recentlyVisited ->
        HomeScreenItem.ProductList(recentlyVisited)
    }

    val contentFlow: Flow<List<HomeScreenItem>> = combine(
        topRatedFlow,
        topSalesFlow,
        randomFlow,
        recentlyVisitedFlow,
    ) { topRated, topSales, random, recentlyVisited ->
        listOf(
            HomeScreenItem.Header(R.string.top_rated),
            topRated,
            HomeScreenItem.Header(R.string.top_sales),
            topSales,
            HomeScreenItem.Header(R.string.you_visited),
            recentlyVisited,
            HomeScreenItem.Header(R.string.explore),
            random,
        )
    }

    private fun productListFlow(block: suspend () -> List<ProductPreview>): Flow<HomeScreenItem> {
        return flow {
            emit(HomeScreenItem.LoadingProductList())

            val products =
                runCatching {
                    block()
                }.getOrElse {
                    Log.e("HomeViewModel", "Got error", it)
                    emptyList()
                }

            emit(HomeScreenItem.ProductList(products))
        }
    }
}
