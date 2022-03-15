package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            HomeScreenItem.DIFF_CALLBACK,
            HomeScreenItem.Headline.delegate,
            HomeScreenItem.Banners.delegate,
            HomeScreenItem.ProductOfTheDayItem.delegate,
            HomeScreenItem.FeaturedTabs.delegate(viewModel::switchFeaturedTab),
            HomeScreenItem.ProductList.delegate(::navigateToProductDetails),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        binding.rvContent.adapter = adapter
        initSearchViewListener()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
//        viewModel.contentFlow
//            .onEach(adapter::setItems)
//            .launchIn(viewLifecycleOwner.lifecycleScope)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.contentFlow.collect(adapter::setItems)
        }
    }

    private fun initSearchViewListener() {
        val searchView = binding.root.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextFocusChangeListener { _, _ ->
            searchView.setOnQueryTextFocusChangeListener(null)
            navigateToSearch()
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToSearch(),
        )
    }

    private fun navigateToProductDetails(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToFragmentProductDetails(id),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
