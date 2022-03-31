package com.narcissus.marketplace.ui.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemCheckoutDetailBinding
import com.narcissus.marketplace.domain.model.CheckoutItem

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
                            R.string.amount_placeholder, item.detail.detailAmount,
                        )
                        binding.tvOrderDetailPrice.text = itemView.context.getString(
                            R.string.price_placeholder, item.detail.detailPrice,
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
