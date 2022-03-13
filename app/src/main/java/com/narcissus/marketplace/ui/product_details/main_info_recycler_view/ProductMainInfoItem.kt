package com.narcissus.marketplace.ui.product_details.main_info_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemDetailsMainInfoListPurchaseButtonActiveBinding
import com.narcissus.marketplace.databinding.ListItemDetailsMainInfoListPurchaseButtonInactiveBinding
import com.narcissus.marketplace.databinding.ListItemDetailsMainInfoListRatingSectionBinding
import com.narcissus.marketplace.ui.product_details.utils.getTextLinearGradient

typealias RatingSectionBinding = ListItemDetailsMainInfoListRatingSectionBinding
typealias PurchaseButtonActiveBinding = ListItemDetailsMainInfoListPurchaseButtonActiveBinding
typealias PurchaseButtonInactiveBinding = ListItemDetailsMainInfoListPurchaseButtonInactiveBinding

sealed class ProductMainInfoItem {

    data class ProductMainInfoRatingSection(
        val rating: Int,
        val salesCount: Int,
        val stockCount: Int,
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = RatingSectionBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<ProductMainInfoRatingSection, ProductMainInfoItem, RatingSectionBinding>(
                        ::inflateBinding
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

    data class ProductMainInfoPurchaseButtonActive(
        @StringRes val titleId: Int
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = PurchaseButtonActiveBinding.inflate(layoutInflater, root, false)

            fun delegate(purchaseButtonListener: () -> Unit) =
                adapterDelegateViewBinding<ProductMainInfoPurchaseButtonActive, ProductMainInfoItem, PurchaseButtonActiveBinding>(
                    ::inflateBinding
                ) {
                    binding.layoutBtnPurchase.setOnClickListener { purchaseButtonListener() }
                    bind {
                        binding.tvBtnPurchaseActive.text = context.getString(item.titleId)
                    }
                }
        }
    }

    data class ProductMainInfoPurchaseButtonInactive(
        @StringRes val titleId: Int
    ) : ProductMainInfoItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = PurchaseButtonInactiveBinding.inflate(layoutInflater, root, false)

            fun delegate(goToCartButtonListener: () -> Unit) =
                adapterDelegateViewBinding<ProductMainInfoPurchaseButtonInactive, ProductMainInfoItem, PurchaseButtonInactiveBinding>(
                    ::inflateBinding
                ) {
                    bind {
                        binding.layoutBtnPurchase.setOnClickListener { goToCartButtonListener() }
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
                newItem: ProductMainInfoItem
            ): Boolean {
                return when (oldItem) {
                    is ProductMainInfoRatingSection -> newItem is ProductMainInfoRatingSection
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: ProductMainInfoItem,
                newItem: ProductMainInfoItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
