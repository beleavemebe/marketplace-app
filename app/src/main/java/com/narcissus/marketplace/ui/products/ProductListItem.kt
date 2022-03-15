package com.narcissus.marketplace.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemProductPreviewBinding
import com.narcissus.marketplace.databinding.ListItemProductPreviewLoadingBinding
import com.narcissus.marketplace.model.ProductPreview

typealias LoadingBinding = ListItemProductPreviewLoadingBinding
typealias ProductPreviewBinding = ListItemProductPreviewBinding

sealed class ProductListItem {
    data class Product(val productPreview: ProductPreview) : ProductListItem() {
        companion object {
            @JvmStatic private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ProductPreviewBinding.inflate(inflater, parent, false)

            fun delegate(onClicked: (id: String) -> Unit) =
                adapterDelegateViewBinding<Product, ProductListItem, ProductPreviewBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.productNameTextView.text = item.productPreview.name
                        binding.productImageView.load(item.productPreview.icon)
                        binding.productRatingBar.progress = item.productPreview.rating * 2
                        binding.productPriceTextView.text = itemView.context.getString(R.string.price_placeholder, item.productPreview.price)
                        binding.productSalesTextView.text = itemView.context.getString(R.string.sales_placeholder, item.productPreview.sales)
                        binding.productStockTextView.text = itemView.context.getString(R.string.in_stock_placeholder, item.productPreview.stock)
                        binding.root.setOnClickListener {
                            onClicked(item.productPreview.id)
                        }
                    }
                }
        }
    }

    class LoadingProduct : ProductListItem() {
        companion object {
            @JvmStatic private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = LoadingBinding.inflate(inflater, parent, false)

            val delegate get() =
                adapterDelegateViewBinding<LoadingProduct, ProductListItem, LoadingBinding>(
                    ::inflateBinding,
                ) {

                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductListItem>() {
            override fun areItemsTheSame(
                oldItem: ProductListItem,
                newItem: ProductListItem,
            ): Boolean {
                return when (oldItem) {
                    is LoadingProduct -> newItem is LoadingProduct && oldItem === newItem
                    is Product -> newItem is Product && oldItem.productPreview.id == oldItem.productPreview.id
                }
            }

            override fun areContentsTheSame(
                oldItem: ProductListItem,
                newItem: ProductListItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
