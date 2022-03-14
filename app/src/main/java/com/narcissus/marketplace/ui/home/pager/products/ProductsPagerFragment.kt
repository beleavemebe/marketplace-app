package com.narcissus.marketplace.ui.home.pager.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeScreenPageBinding
import com.narcissus.marketplace.ui.home.HomeFragmentDirections
import com.narcissus.marketplace.ui.home.HomeViewModel
import com.narcissus.marketplace.ui.products.ProductListItem
import com.narcissus.marketplace.ui.products.ProductsDelegationAdapter
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.navigation.koinNavGraphViewModel

abstract class ProductsPagerFragment : Fragment(R.layout.fragment_home_screen_page) {
    private var _binding: FragmentHomeScreenPageBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        ProductsDelegationAdapter(::navigateToProductDetails)
    }

    protected val viewModel by koinNavGraphViewModel<HomeViewModel>(R.id.nav_graph)

    abstract val contentFlow: Flow<List<ProductListItem>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeScreenPageBinding.bind(view)
        initRecyclerView()
        subscribeToViewModel()
    }

    private fun initRecyclerView() {
        binding.rvPageContent.adapter = adapter
        binding.rvPageContent.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            contentFlow.collect(adapter::setItems)
        }
    }

    private fun navigateToProductDetails(id: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionFragmentHomeToFragmentProductDetails(id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
