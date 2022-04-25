package com.narcissus.marketplace.ui.product_details.main_info_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.narcissus.marketplace.ui.product_details.R
import com.narcissus.marketplace.ui.product_details.databinding.ListItemDetailsMainInfoListPurchaseButtonActiveBinding
import com.narcissus.marketplace.ui.product_details.databinding.ListItemDetailsMainInfoListPurchaseButtonInactiveBinding
import com.narcissus.marketplace.ui.product_details.databinding.ListItemDetailsMainInfoListRatingSectionBinding
import com.narcissus.marketplace.ui.product_details.utils.getTextLinearGradient
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

typealias RatingSectionBinding = ListItemDetailsMainInfoListRatingSectionBinding
typealias PurchaseButtonActiveBinding = ListItemDetailsMainInfoListPurchaseButtonActiveBinding
typealias PurchaseButtonInactiveBinding = ListItemDetailsMainInfoListPurchaseButtonInactiveBinding

sealed class ProductMainInfoItem {
    data class RatingSection(
        val rating: Int,
        val salesCount: Int,
        val stockCount: Int,
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = RatingSectionBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<RatingSection, ProductMainInfoItem, RatingSectionBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.ratingBarDetailsProduct.progress = item.rating
                        binding.tvDetailsSales.text =
                            context.getString(R.string.sales_placeholder, item.salesCount)
                        binding.tvDetailsStock.text =
                            context.getString(R.string.in_stock_placeholder, item.stockCount)
                    }
                }
        }
    }

    data class ActivePurchaseButton(
        @StringRes val titleId: Int,
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = PurchaseButtonActiveBinding.inflate(layoutInflater, root, false)

            fun delegate(onPurchaseClicked: () -> Unit) =
                adapterDelegateViewBinding<ActivePurchaseButton, ProductMainInfoItem, PurchaseButtonActiveBinding>(
                    ::inflateBinding,
                ) {
                    binding.layoutBtnPurchase.setOnClickListener { onPurchaseClicked() }
                    bind {
                        binding.tvBtnPurchaseActive.text = context.getString(item.titleId)
                    }
                }
        }
    }

    data class InactivePurchaseButton(
        @StringRes val titleId: Int,
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = PurchaseButtonInactiveBinding.inflate(layoutInflater, root, false)

            fun delegate(onGoToCartClicked: () -> Unit) =
                adapterDelegateViewBinding<InactivePurchaseButton, ProductMainInfoItem, PurchaseButtonInactiveBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.layoutBtnPurchase.setOnClickListener { onGoToCartClicked() }
                        binding.tvBtnPurchaseInactive.paint.shader = getTextLinearGradient(context)
                        binding.tvBtnPurchaseInactive.text = context.getString(item.titleId)
                    }
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductMainInfoItem>() {
            override fun areItemsTheSame(
                oldItem: ProductMainInfoItem,
                newItem: ProductMainInfoItem,
            ): Boolean {
                return when (oldItem) {
                    is RatingSection -> newItem is RatingSection
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: ProductMainInfoItem,
                newItem: ProductMainInfoItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
