package com.narcissus.marketplace.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemHomeScreenHeaderBinding
import com.narcissus.marketplace.databinding.ListItemLoadingProductListBinding
import com.narcissus.marketplace.databinding.ListItemProductListBinding
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.ui.home.HomeFragment.Companion.HOME_SCREEN_MARGINS
import com.narcissus.marketplace.ui.products.ProductsAdapter

typealias HeaderBinding = ListItemHomeScreenHeaderBinding
typealias ProductListBinding = ListItemProductListBinding
typealias LoadingProductListBinding = ListItemLoadingProductListBinding

sealed class HomeScreenItem {
    data class Header(@StringRes val titleResId: Int) : HomeScreenItem() {
        companion object {
            @JvmStatic private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = HeaderBinding.inflate(layoutInflater, root, false)

            val delegate get() =
                adapterDelegateViewBinding<Header, HomeScreenItem, HeaderBinding>(
                    ::inflateBinding
                ) {
                    bind {
                        binding.tvHeaderTitle.text = context.getString(item.titleResId)
                    }
                }
        }
    }

    data class ProductList(val products: List<ProductPreview>) : HomeScreenItem() {
        companion object {
            @JvmStatic private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ProductListBinding.inflate(layoutInflater, root, false)

            fun delegate(onProductClicked: (id: String) -> Unit) =
                adapterDelegateViewBinding<ProductList, HomeScreenItem, ProductListBinding>(
                    ::inflateBinding
                ) {
                    val adapter = ProductsAdapter(onProductClicked)
                    binding.rvProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvProducts.addItemDecoration(ExtraHorizontalMarginDecoration(HOME_SCREEN_MARGINS))
                    binding.rvProducts.adapter = adapter

                    bind {
                        adapter.submitItems(item.products)
                    }
                }
        }
    }

    class LoadingProductList : HomeScreenItem() {
        companion object {
            @JvmStatic private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = LoadingProductListBinding.inflate(layoutInflater, root, false)

            val delegate get() =
                adapterDelegateViewBinding<LoadingProductList, HomeScreenItem, LoadingProductListBinding>(
                    ::inflateBinding,
                ) {
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeScreenItem>() {
            override fun areItemsTheSame(
                oldItem: HomeScreenItem,
                newItem: HomeScreenItem
            ): Boolean {
                return when (oldItem) {
                    is Header -> newItem is Header && oldItem.titleResId == newItem.titleResId
                    is ProductList -> newItem is ProductList && oldItem.products == newItem.products
                    is LoadingProductList -> newItem is LoadingProductList && oldItem === newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: HomeScreenItem,
                newItem: HomeScreenItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
