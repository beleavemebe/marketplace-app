package com.narcissus.marketplace.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.ui.products.ProductListItem
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val getTopRatedProducts: GetTopRatedProducts,
    private val getTopSalesProducts: GetTopSalesProducts,
    private val getRandomProducts: GetRandomProducts,
    private val getRecentlyVisitedProducts: GetRecentlyVisitedProducts,
) : ViewModel() {
    val topRatedFlow = productListFlow {
        getTopRatedProducts().getOrThrow()
    }

    val topSalesFlow = productListFlow {
        getTopSalesProducts().getOrThrow()
    }

    val randomFlow = productListFlow {
        getRandomProducts().getOrThrow()
    }

     val recentlyVisitedFlow = getRecentlyVisitedProducts()
         .map { recentlyVisited ->
             recentlyVisited.map { preview -> ProductListItem.Product(preview) }
        }

    private fun productListFlow(block: suspend () -> List<ProductPreview>): Flow<List<ProductListItem>> {
        return flow {
            val dummyContent = Array(8) { ProductListItem.LoadingProduct() }.asList()
            emit(dummyContent)

            val products =
                runCatching {
                    block()
                }.getOrElse {
                    Log.e("HomeViewModel", "Got error", it)
                    emptyList()
                }

            val items = products.map { preview -> ProductListItem.Product(preview) }
            emit(items)
        }
    }
}
