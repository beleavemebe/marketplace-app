package com.narcissus.marketplace.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.domain.model.ProductOfTheDay
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.SpecialOfferBanner
import com.narcissus.marketplace.domain.usecase.GetPeopleAreBuyingProducts
import com.narcissus.marketplace.domain.usecase.GetRandomProducts
import com.narcissus.marketplace.domain.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.domain.usecase.GetTopRatedProducts
import com.narcissus.marketplace.domain.usecase.GetTopSalesProducts
import com.narcissus.marketplace.ui.home.recycler.FeaturedTab
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import com.narcissus.marketplace.ui.home.recycler.ProductOfTheDayItem
import com.narcissus.marketplace.ui.products.ProductListItem
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
    private val getPeopleAreBuyingProducts: GetPeopleAreBuyingProducts,
    getRecentlyVisitedProducts: GetRecentlyVisitedProducts,
) : ViewModel() {

    private val topRatedFlow = productListFlow {
        getTopRatedProducts()
    }

    private val topSalesFlow = productListFlow {
        getTopSalesProducts()
    }

    private val randomFlow = productListFlow {
        getRandomProducts()
    }

    private val peopleAreBuyingFlow = productListFlow {
        getPeopleAreBuyingProducts()
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
                    "5fffb0a82786863d4f4d2798",
                    "https://dummyproducts-api.herokuapp.com/appliances/vacuumcleaner_600.png",
                    "Drab vacuum cleaner",
                    459,
                    229,
                    50,
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
        peopleAreBuyingFlow,
    ) { banner, productsOfTheDay, featuredContent, recentlyVisited, peopleAreBuying ->
        val sectionBanners = listOf(
            HomeScreenItem.Headline(R.string.special_offer),
            HomeScreenItem.Banners(banner),
        )

        val sectionProductsOfTheDay = listOf(
            HomeScreenItem.Headline(R.string.products_of_the_day),
            HomeScreenItem.ProductsOfTheDay(productsOfTheDay),
        )

        val sectionFeatured = listOf(
            HomeScreenItem.Headline(R.string.featured),
            HomeScreenItem.FeaturedTabs(),
            HomeScreenItem.Products(featuredContent),
        )

        val sectionPeopleAreBuying = listOf(
            HomeScreenItem.Headline(R.string.people_are_buying),
            HomeScreenItem.Products(peopleAreBuying),
        ).takeIf {
            peopleAreBuying.isNotEmpty()
        }.orEmpty()

        val sectionYouViewed = listOf(
            HomeScreenItem.Headline(R.string.you_viewed),
            HomeScreenItem.Products(recentlyVisited),
        ).takeIf {
            recentlyVisited.isNotEmpty()
        }.orEmpty()

        val sectionThatsIt = listOf(
            HomeScreenItem.Headline(R.string.home_screen_footer),
        )

        listOf(
            sectionBanners,
            sectionProductsOfTheDay,
            sectionFeatured,
            sectionPeopleAreBuying,
            sectionYouViewed,
            sectionThatsIt
        ).flatten()
    }

    private fun productListFlow(block: suspend () -> List<ProductPreview>): Flow<List<ProductListItem>> {
        return flow {
            val dummyContent = Array(8) { ProductListItem.LoadingProduct() }.asList()
            emit(dummyContent)

            val products =
                runCatching {
                    block()
                }.getOrElse {
                    Log.e("HomeViewModel", "Got error $it", it)
                    emptyList()
                }

            val items = products.map { preview -> ProductListItem.Product(preview) }
            emit(items)
        }
    }
}
