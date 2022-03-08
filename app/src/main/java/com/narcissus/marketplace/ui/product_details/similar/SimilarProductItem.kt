package com.narcissus.marketplace.ui.product_details.similar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemDetailsSimilarProductItemBinding
import com.narcissus.marketplace.databinding.ListItemDetailsSimilarProductLoadingBinding
import com.narcissus.marketplace.model.SimilarProduct

typealias SimilarProductBinding = ListItemDetailsSimilarProductItemBinding
typealias SimilarProductLoadingBinding = ListItemDetailsSimilarProductLoadingBinding
sealed class SimilarProductListItem {
    class SimilarProductItem(val similarProduct: SimilarProduct, val rootOnClickListener: (productId: String) -> Unit) : SimilarProductListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = SimilarProductBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<SimilarProductItem, SimilarProductListItem, SimilarProductBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            with(binding) {
                                ivSimilarProduct.load(item.similarProduct.icon)
                                tvSimilarProductName.text = item.similarProduct.name
                                tvSimilarProductPrice.text = binding.root.context.getString(
                                    R.string.price_placeholder,
                                    item.similarProduct.price
                                )
                                similarProductRatingBar.progress = item.similarProduct.rating
                                tvSimilarProductStock.text = binding.root.context.getString(
                                    R.string.in_stock_placeholder,
                                    item.similarProduct.stock
                                )
                                root.setOnClickListener { item.rootOnClickListener(item.similarProduct.id) }
                                btnSimilarProductAddToCart.setOnClickListener {}
                            }
                        }
                    }
        }
    }
    class SimilarProductLoadingItem : SimilarProductListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = SimilarProductLoadingBinding.inflate(layoutInflater, root, false)
            val delegate
                get() =
                    adapterDelegateViewBinding<SimilarProductLoadingItem, SimilarProductListItem, SimilarProductLoadingBinding>(
                        ::inflateBinding
                    ) {
                        bind {}
                    }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SimilarProductListItem>() {
            override fun areItemsTheSame(
                oldItem: SimilarProductListItem,
                newItem: SimilarProductListItem
            ): Boolean {
                return when (newItem) {
                    is SimilarProductItem -> oldItem is SimilarProductItem && oldItem.similarProduct.id == newItem.similarProduct.id
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: SimilarProductListItem,
                newItem: SimilarProductListItem
            ): Boolean = oldItem == newItem
        }
    }
}
