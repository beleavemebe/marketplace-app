package com.narcissus.marketplace.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCartBinding
import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductPreview

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private var cartAdapter = CartAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        initRView()
        fillData()
    }

    private fun initRView() {
        binding.cartRView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    private fun fillData(){
        val sampleCartItem = ProductPreview("1", "", 1449,"Apple MacBook Pro 13","","",752,"","",3,152)
        val cartItemsList: List<CartItem> = listOf(
            CartItem(sampleCartItem,1),
            CartItem(sampleCartItem,2)
        )
        cartAdapter.setData(cartItemsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
