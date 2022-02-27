package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.recycler.ExtraVerticalMarginDecoration
import com.narcissus.marketplace.ui.home.recycler.HomeScreenAdapter
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeScreenAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecyclerView()
        fillRecyclerWithDummyContent()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        binding.rvContent.addItemDecoration(ExtraVerticalMarginDecoration(HOME_SCREEN_MARGINS))
        binding.rvContent.adapter = adapter
    }

    // TODO: move initial dummy content to HomeViewModel
    private fun fillRecyclerWithDummyContent() {
        adapter.items = listOf(
            HomeScreenItem.Header(
                requireContext().getString(R.string.top_rated)
            ),
            HomeScreenItem.LoadingProductList(),

            HomeScreenItem.Header(
                requireContext().getString(R.string.top_sales)
            ),
            HomeScreenItem.LoadingProductList(),

            HomeScreenItem.Header(
                requireContext().getString(R.string.you_visited)
            ),
            HomeScreenItem.LoadingProductList(),

            HomeScreenItem.Header(
                requireContext().getString(R.string.explore)
            ),
            HomeScreenItem.LoadingProductList()
        )
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            observeTopRatedProducts()
            observeTopSalesProducts()
            observeRecentlyVisitedProducts()
            observeRandomProducts()
        }
    }

    private suspend fun observeTopRatedProducts() {
        viewModel.topRatedFlow.collect { items ->
            adapter.items = adapter.items.modifiedAt(
                TOP_RATED_PRODUCTS_INDEX,
                HomeScreenItem.ProductList(items)
            )
        }
    }

    private suspend fun observeTopSalesProducts() {
        viewModel.topSalesFlow.collect { items ->
            adapter.items = adapter.items.modifiedAt(
                TOP_SALES_PRODUCTS_INDEX,
                HomeScreenItem.ProductList(items)
            )
        }
    }

    private suspend fun observeRecentlyVisitedProducts() {
        viewModel.recentlyVisitedFlow.collect { items ->
            adapter.items = adapter.items.modifiedAt(
                RECENTLY_VISITED_PRODUCTS_INDEX,
                HomeScreenItem.ProductList(items)
            )
        }
    }

    private suspend fun observeRandomProducts() {
        viewModel.randomFlow.collect { result ->
            adapter.items = adapter.items.modifiedAt(
                EXPLORE_PRODUCTS_INDEX,
                HomeScreenItem.ProductList(result.data)
            )
        }
    }

    private fun List<HomeScreenItem>.modifiedAt(
        index: Int,
        with: HomeScreenItem
    ): List<HomeScreenItem> {
        val result = this.toMutableList()
        result[index] = with
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvContent.adapter = null
        _binding = null
    }

    companion object {
        const val HOME_SCREEN_MARGINS = 8

        const val TOP_RATED_PRODUCTS_INDEX = 1
        const val TOP_SALES_PRODUCTS_INDEX = 3
        const val RECENTLY_VISITED_PRODUCTS_INDEX = 5
        const val EXPLORE_PRODUCTS_INDEX = 7
    }
}
