package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.recycler.ExtraVerticalMarginDecoration
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecyclerView()
        fillRecyclerWithDummyContent()
        subscribeToViewModel()
    }

    private val adapter = object : AsyncListDifferDelegationAdapter<HomeScreenItem>(
        HomeScreenItem.DIFF_CALLBACK,
        HomeScreenItem.Header.delegate,
        HomeScreenItem.ProductList.delegate,
        HomeScreenItem.LoadingProductList.delegate,
    ) {
        override fun setItems(items: MutableList<HomeScreenItem>?) {
            println("Got Items: ${items?.joinToString { it.javaClass.simpleName }}")
            super.setItems(items)
        }
    }

    private fun initRecyclerView() {
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
            viewModel.topRatedFlow
                .flowOn(Dispatchers.Main) // TODO: find another way to escape race condition
                .collect { items ->
                    adapter.items = adapter.items.modifiedAt(
                        TOP_RATED_PRODUCTS_INDEX,
                        HomeScreenItem.ProductList(items)
                    )
            }

            viewModel.topSalesFlow
                .flowOn(Dispatchers.Main) // TODO: find another way to escape race condition
                .collect { items ->
                    adapter.items = adapter.items.modifiedAt(
                        TOP_SALES_PRODUCTS_INDEX,
                        HomeScreenItem.ProductList(items)
                    )
            }

            viewModel.recentlyVisitedFlow
                .flowOn(Dispatchers.Main) // TODO: find another way to escape race condition
                .collect { items ->
                    adapter.items = adapter.items.modifiedAt(
                        RECENTLY_VISITED_PRODUCTS_INDEX,
                        HomeScreenItem.ProductList(items)
                    )
            }

            viewModel.randomFlow
                .flowOn(Dispatchers.Main) // TODO: find another way to escape race condition
                .collect { result ->
                    adapter.items = adapter.items.modifiedAt(
                        EXPLORE_PRODUCTS_INDEX,
                        HomeScreenItem.ProductList(result.data)
                    )
            }
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
