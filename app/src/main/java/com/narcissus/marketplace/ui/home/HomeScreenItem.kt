package com.narcissus.marketplace.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemHomeScreenHeaderBinding
import com.narcissus.marketplace.databinding.ListItemHomeScreenProductListBinding
import com.narcissus.marketplace.model.ProductPreview

typealias HeaderBinding = ListItemHomeScreenHeaderBinding
typealias ProductListBinding = ListItemHomeScreenProductListBinding

open class HomeScreenItem {
    data class Header(val title: String) : HomeScreenItem() {
        companion object {
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = HeaderBinding.inflate(layoutInflater, root, false)

            val delegate get() =
                adapterDelegateViewBinding<Header, HomeScreenItem, HeaderBinding>(
                    ::inflateBinding
                ) {
                    bind {
                        binding.tvHeaderTitle.text = item.title
                    }
                }
        }
    }

    data class ProductList(val products: List<ProductPreview>) : HomeScreenItem() {
        companion object {
            private const val EXTRA_LEFT_MARGIN = 8

            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ProductListBinding.inflate(layoutInflater, root, false)

            val delegate get() =
                adapterDelegateViewBinding<ProductList, HomeScreenItem, ProductListBinding>(
                    ::inflateBinding
                ) {
                    val adapter = ProductsAdapter()
                    binding.rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvProducts.addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
                    binding.rvProducts.adapter = adapter

                    bind {
                        adapter.submitItems(item.products)
                    }
                }
        }
    }
}
