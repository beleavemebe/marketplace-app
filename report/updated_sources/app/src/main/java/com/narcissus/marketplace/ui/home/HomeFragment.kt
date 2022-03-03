package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.ui.home.recycler.ExtraVerticalMarginDecoration
import com.narcissus.marketplace.ui.home.recycler.HomeScreenAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeScreenAdapter(::navigateToProductDetails)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecyclerView()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        binding.rvContent.addItemDecoration(ExtraVerticalMarginDecoration(HOME_SCREEN_MARGINS))
        binding.rvContent.adapter = adapter
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.contentFlow.collect(adapter::setItems)
        }
    }

    private fun navigateToProductDetails(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToProductDetailsFragment()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvContent.adapter = null
        _binding = null
    }

    companion object {
        const val HOME_SCREEN_MARGINS = 8
    }
}
