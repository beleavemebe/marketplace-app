package com.narcissus.marketplace.ui.cart.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.narcissus.marketplace.ui.cart.databinding.ListItemCheckoutDetailBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.domain.model.CheckoutItem
import com.narcissus.marketplace.core.R as CORE

typealias DetailBinding = ListItemCheckoutDetailBinding

sealed class CheckoutListItem {

    data class Detail(val detail: CheckoutItem) : CheckoutListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = DetailBinding.inflate(inflater, parent, false)

            fun delegate() =
                adapterDelegateViewBinding<Detail, CheckoutListItem, DetailBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvOrderDetailName.text = item.detail.detailName
                        binding.tvOrderDetailAmount.text = itemView.context.getString(
                            CORE.string.amount_placeholder, item.detail.detailAmount,
                        )
                        binding.tvOrderDetailPrice.text = itemView.context.getString(
                            CORE.string.price_placeholder, item.detail.detailPrice,
                        )
                    }
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CheckoutListItem>() {
            override fun areItemsTheSame(
                oldItem: CheckoutListItem,
                newItem: CheckoutListItem,
            ): Boolean {
                return when (oldItem) {
                    is Detail -> newItem is Detail && oldItem.detail.detailId == newItem.detail.detailId
                }
            }

            override fun areContentsTheSame(
                oldItem: CheckoutListItem,
                newItem: CheckoutListItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
