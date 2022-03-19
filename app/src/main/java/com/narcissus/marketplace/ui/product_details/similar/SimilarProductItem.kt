package com.narcissus.marketplace.ui.product_details.similar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemDetailsSimilarProductItemBinding
import com.narcissus.marketplace.databinding.ListItemProductPreviewLoadingBinding
import com.narcissus.marketplace.domain.model.SimilarProduct
import com.narcissus.marketplace.ui.product_details.utils.getTextLinearGradient

typealias SimilarProductBinding = ListItemDetailsSimilarProductItemBinding
typealias SimilarProductLoadingBinding = ListItemProductPreviewLoadingBinding

sealed class SimilarProductListItem {
    data class SimilarProductItem(
        val product: SimilarProduct,
        val isButtonAddToCartActive: Boolean,
    ) :
        SimilarProductListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = SimilarProductBinding.inflate(layoutInflater, root, false)

            fun delegate(
                onProductClicked: (productId: String, cardView: MaterialCardView) -> Unit,
                onAddToCartClicked: (productId: String) -> Unit,
            ) =
                adapterDelegateViewBinding<SimilarProductItem, SimilarProductListItem, SimilarProductBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.ivSimilarProduct.load(item.product.icon)
                        binding.tvSimilarProductName.text = item.product.name
                        binding.tvSimilarProductPrice.text = binding.root.context.getString(
                            R.string.price_placeholder,
                            item.product.price,
                        )
                        binding.similarProductRatingBar.progress = item.product.rating
                        binding.tvSimilarProductStock.text = binding.root.context.getString(
                            R.string.in_stock_placeholder,
                            item.product.stock,
                        )
                        binding.root.setOnClickListener {
                            binding.root.transitionName = item.product.id
                            onProductClicked(item.product.id, binding.root)
                        }
                        if (item.isButtonAddToCartActive) {
                            binding.tvBtnSimilarProductAddToCart.text = context.getString(R.string.add_to_cart)
                            binding.tvBtnSimilarProductAddToCart.paint.shader = null
                            binding.layoutBtnSimilarProductAddToCart.background =
                                getDrawable(R.drawable.button_5dp_corners_gradient_background)
                        } else {
                            binding.tvBtnSimilarProductAddToCart.text = context.getString(R.string.go_to_cart)
                            binding.tvBtnSimilarProductAddToCart.paint.shader = getTextLinearGradient(context)
                            binding.layoutBtnSimilarProductAddToCart.background =
                                getDrawable(R.drawable.button_5dp_corners_gradient_inactive_background)
                        }

                        binding.layoutBtnSimilarProductAddToCart.setOnClickListener {
                            onAddToCartClicked(item.product.id)
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
                root: ViewGroup,
            ) = SimilarProductLoadingBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<SimilarProductLoadingItem, SimilarProductListItem, SimilarProductLoadingBinding>(
                        ::inflateBinding,
                    ) {
                    }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SimilarProductListItem>() {
            override fun areItemsTheSame(
                oldItem: SimilarProductListItem,
                newItem: SimilarProductListItem,
            ): Boolean {
                return when (oldItem) {
                    is SimilarProductItem ->
                        newItem is SimilarProductItem && oldItem.product.id == newItem.product.id &&
                            oldItem.isButtonAddToCartActive == newItem.isButtonAddToCartActive
                    is SimilarProductLoadingItem -> newItem is SimilarProductLoadingItem && oldItem === newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: SimilarProductListItem,
                newItem: SimilarProductListItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
