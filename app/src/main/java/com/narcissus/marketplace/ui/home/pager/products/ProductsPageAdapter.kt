package com.narcissus.marketplace.ui.home.pager.products

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.ui.home.pager.products.pages.ExploreFragment
import com.narcissus.marketplace.ui.home.pager.products.pages.RecentlyVisitedFragment
import com.narcissus.marketplace.ui.home.pager.products.pages.TopRatedFragment
import com.narcissus.marketplace.ui.home.pager.products.pages.TopSalesFragment

class ProductsPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    companion object {
        private val pages = listOf(
            ::TopRatedFragment,
            ::TopSalesFragment,
            ::RecentlyVisitedFragment,
            ::ExploreFragment,
        )

        private val titles = listOf(
            R.string.top_rated,
            R.string.top_sales,
            R.string.you_viewed,
            R.string.explore,
        )

        fun getTitle(position: Int): Int {
            return titles[position]
        }
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position].invoke()
    }
}
