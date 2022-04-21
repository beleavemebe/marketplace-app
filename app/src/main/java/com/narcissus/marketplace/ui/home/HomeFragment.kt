package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialFadeThrough
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.destination.ProductDetailsDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.recycler.HomeScreenItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            HomeScreenItem.DIFF_CALLBACK,
            HomeScreenItem.Headline.delegate(),
            HomeScreenItem.Banners.delegate(::navigateToSpecialOffer),
            HomeScreenItem.ProductsOfTheDay.delegate(::navigateToProductDetails),
            HomeScreenItem.FeaturedTabs.delegate(viewModel::switchFeaturedTab),
            HomeScreenItem.Products.delegate(::navigateToProductDetails),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        renderTransition()
        binding.rvContent.adapter = adapter
        initSearchViewListener()
        subscribeToViewModel()
    }

    private fun renderTransition() {
        postponeEnterTransition()
        binding.rvContent.post { startPostponedEnterTransition() }
    }

    private fun subscribeToViewModel() {
        viewModel.contentFlow
            .onEach(adapter::setItems)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initSearchViewListener() {
        val searchView = binding.root.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextFocusChangeListener { _, _ ->
            searchView.setOnQueryTextFocusChangeListener(null)
            navigateToSearch()
        }
    }

    private fun navigateToSearch() {
    }

    private fun navigateToSpecialOffer(link: String) {
        // todo: handle deep link
    }

    private fun navigateToProductDetails(id: String, cardView: MaterialCardView) {
        val productDetailsDestination = ProductDetailsDestination(id)
        val extras = FragmentNavigatorExtras(cardView to id)
        navigator.navigate(productDetailsDestination, extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
