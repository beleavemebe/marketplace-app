package com.narcissus.marketplace.ui.home

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.pager.banner.Banner
import com.narcissus.marketplace.ui.home.pager.products.ProductsPageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initBannerPager()
        initProductsPager()
        initSearchViewListener()
    }

    private fun initBannerPager() {
        val bannerAdapter = ListDelegationAdapter(Banner.delegate())
        binding.vpBanners.adapter = bannerAdapter
        bannerAdapter.items = listOf(
            Banner("https://c.tenor.com/UjdeUF--bBkAAAAS/sussy.gif") {},
            Banner("https://c.tenor.com/UjdeUF--bBkAAAAS/sussy.gif") {},
        )
    }

    private fun initProductsPager() {
        binding.vpPages.adapter = ProductsPageAdapter(this)
        binding.vpPages.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.tlPages.addOnTabSelectedListener(createOnProductsTabSelectedListener())

        TabLayoutMediator(
            binding.tlPages, binding.vpPages,
        ) { tab, position ->
            tab.setCustomView(R.layout.tab_view)
            (tab.customView as TextView).text =
                requireContext().getString(ProductsPageAdapter.getTitle(position))
        }.attach()
    }

    private val increaseTabAnimator by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.animator.increase_tab)
    }

    private val decreaseTabAnimator by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.animator.decrease_tab)
    }

    private fun createOnProductsTabSelectedListener() =
        object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tvToIncrease = tab.customView as? TextView ?: return
                increaseTabAnimator.setTarget(tvToIncrease)
                increaseTabAnimator.start()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tvToDecrease = tab.customView as? TextView ?: return
                decreaseTabAnimator.setTarget(tvToDecrease)
                decreaseTabAnimator.start()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
