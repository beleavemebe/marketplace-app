package com.narcissus.marketplace.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListCartBinding
import com.narcissus.marketplace.model.CartItem


typealias CartItemsBinding = ListCartBinding

open class CartItems {

    data class ItemsList(val items: List<CartItem>) : CartItems() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = CartItemsBinding.inflate(layoutInflater, root, false)

            val adapter = CartAdapter()
            val delegate get() = adapterDelegateViewBinding<ItemsList, CartItems, CartItemsBinding>(
                ::inflateBinding
            ) {
                binding.rvCartItems.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvCartItems.adapter = adapter
                bind{
                    adapter.setData(item.items)
                }
            }

        }
    }
}