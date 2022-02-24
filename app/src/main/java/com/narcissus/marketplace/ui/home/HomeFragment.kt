package com.narcissus.marketplace.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentHomeBinding
import com.narcissus.marketplace.model.ProductPreview

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initRecyclerView()
        fillRecyclerWithDummyContent()
    }

    private val adapter = ListDelegationAdapter(
        HomeScreenItem.Header.delegate,
        HomeScreenItem.ProductList.delegate,
    )

    private fun initRecyclerView() {
        binding.rvContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvContent.adapter = adapter
    }

    private fun fillRecyclerWithDummyContent() {
        val dummyProducts = listOf(
            ProductPreview("1", "", 1449,"Apple MacBook Pro 13","","",752,"","",3,152),
            ProductPreview("2", "", 1299,"Apple MacBook Air 13","","",1021,"","",5,196),
            ProductPreview("3", "", 2199,"Apple MacBook Pro 16","","",128,"","",4,65),
        )

        adapter.items = listOf(
            HomeScreenItem.Header(
                requireContext().getString(R.string.top_rated)
            ),
            HomeScreenItem.ProductList(dummyProducts),

            HomeScreenItem.Header(
                requireContext().getString(R.string.top_sales)
            ),
            HomeScreenItem.ProductList(dummyProducts),

            HomeScreenItem.Header(
                requireContext().getString(R.string.you_visited)
            ),
            HomeScreenItem.ProductList(dummyProducts),

            HomeScreenItem.Header(
                requireContext().getString(R.string.explore)
            ),
            HomeScreenItem.ProductList(dummyProducts)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvContent.adapter = null
        _binding = null
    }
}
