package com.narcissus.marketplace.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.domain.model.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

sealed class CartListItem {
    data class Item(
        val cartItem: CartItem,
    ) : CartListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ListItemCartBinding.inflate(layoutInflater, root, false)

            fun delegate(
                onRemoveClicked: (CartItem) -> Unit,
                onItemChecked: (CartItem, Boolean) -> Unit,
                onItemAmountChanged: (CartItem, Int) -> Unit,
                scope: CoroutineScope,
            ) = adapterDelegateViewBinding<Item, CartListItem, ListItemCartBinding>(
                ::inflateBinding
            ) {
                bind {
                    binding.tvName.text = item.cartItem.data.name
                    binding.tvPrice.text = itemView.context.getString(
                        R.string.price_placeholder,
                        item.cartItem.data.price
                    )

                    binding.ivIcon.load(item.cartItem.data.icon)

                    binding.cbSelected.isChecked = item.cartItem.isSelected
                    binding.cbSelected.setOnCheckedChangeListener { _, isChecked ->
                        onItemChecked(item.cartItem, isChecked)
                    }

                    binding.ibDelete.setOnClickListener {
                        onRemoveClicked(item.cartItem)
                    }

                    binding.productAmount.setAmount(item.cartItem.count)
                    binding.productAmount.amountFlow
                        .onEach { amount ->
                            onItemAmountChanged(item.cartItem, amount)
                        }
                        .launchIn(scope)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CartListItem>() {
            override fun areItemsTheSame(
                oldItem: CartListItem,
                newItem: CartListItem
            ): Boolean {
                return when (oldItem) {
                    is Item -> newItem is Item && oldItem.cartItem.data.id == newItem.cartItem.data.id
                }
            }

            override fun areContentsTheSame(
                oldItem: CartListItem,
                newItem: CartListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
