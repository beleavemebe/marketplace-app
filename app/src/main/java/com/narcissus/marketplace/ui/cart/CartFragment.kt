package com.narcissus.marketplace.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCartBinding

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private var _cartAdapter: CartAdapter? = null
    private val binding get() = _binding!!
    private val cartAdapter get() = _cartAdapter!!
    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        initRecyclerView()
        fillData()
    }

    private fun initRecyclerView() {
        _cartAdapter = CartAdapter()
        binding.rvCartItems.adapter = cartAdapter
    }

    private fun fillData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCartFlow.collect { items ->
                cartAdapter.setData(items)
                "goods: ${items.size}".also { binding.tvProductsAmount.text = it } // temporary
            }
            viewModel.getCartCostFlow.collect { price ->
                binding.tvTotalPrice.text = price
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _cartAdapter = null
        _binding = null
    }
}
