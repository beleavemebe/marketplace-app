package com.narcissus.marketplace.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.ui.home.recycler.FeaturedTab
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import com.narcissus.marketplace.model.ProductOfTheDay
import com.narcissus.marketplace.model.SpecialOfferBanner
import com.narcissus.marketplace.ui.home.recycler.ProductOfTheDayItem
import com.narcissus.marketplace.ui.products.ProductListItem
import com.narcissus.marketplace.usecase.GetRandomProducts
import com.narcissus.marketplace.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.usecase.GetTopRatedProducts
import com.narcissus.marketplace.usecase.GetTopSalesProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getTopRatedProducts: GetTopRatedProducts,
    private val getTopSalesProducts: GetTopSalesProducts,
    private val getRandomProducts: GetRandomProducts,
    getRecentlyVisitedProducts: GetRecentlyVisitedProducts,
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

    private val featuredTabFlow = MutableStateFlow(FeaturedTab.TOP_RATED)
    private val featuredContentFlow = featuredTabFlow
        .flatMapLatest { featuredTab ->
            when (featuredTab) {
                FeaturedTab.TOP_RATED -> topRatedFlow
                FeaturedTab.TOP_SALES -> topSalesFlow
                FeaturedTab.EXPLORE -> randomFlow
            }
        }

    fun switchFeaturedTab(selectedTab: FeaturedTab) {
        viewModelScope.launch {
            featuredTabFlow.emit(selectedTab)
        }
    }

    private val recentlyVisitedFlow = getRecentlyVisitedProducts()
        .map { recentlyVisited ->
            recentlyVisited.map { preview -> ProductListItem.Product(preview) }
        }

    private val bannersFlow = flow {
        emit(
            SpecialOfferBanner("https://png.pngtree.com/background/20210714/original/pngtree-black-friday-banners-sale-web-market-picture-image_1239954.jpg", ""),
        )
    }

    private val productsOfTheDayFlow = flow {
        emit(
            listOf(
                ProductOfTheDay(
                    "5fffaea083fde83c1b4ead5b",
                    "https://dummyproducts-api.herokuapp.com/appliances/wallfan_600.png",
                    "Wall fan",
                    936,
                    624,
                    30,
                ),
                ProductOfTheDay(
                    "5fffaea083fde83c1b4ead5b",
                    "https://dummyproducts-api.herokuapp.com/appliances/wallfan_600.png",
                    "Wall fan",
                    936,
                    624,
                    30,
                ),
            ).map {
                ProductOfTheDayItem(it)
            }
        )
    }

    val contentFlow: Flow<List<HomeScreenItem>> = combine(
        bannersFlow,
        productsOfTheDayFlow,
        featuredContentFlow,
        recentlyVisitedFlow,
    ) { banner, productsOfTheDay, featuredContent, recentlyVisited ->
        listOf(
            HomeScreenItem.Headline(R.string.special_offer),
            HomeScreenItem.Banners(banner),
            HomeScreenItem.Headline(R.string.product_of_the_day),
            HomeScreenItem.ProductsOfTheDay(productsOfTheDay),
            HomeScreenItem.Headline(R.string.featured),
            HomeScreenItem.FeaturedTabs(),
            HomeScreenItem.Products(featuredContent),
            HomeScreenItem.Headline(R.string.you_viewed),
            HomeScreenItem.Products(recentlyVisited),
            HomeScreenItem.Headline(R.string.home_screen_footer),
        )
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
