package com.narcissus.marketplace.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListCartBinding
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.model.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


typealias CartItemsBinding = ListCartBinding

sealed class CartListItem {
    data class Item(
        val cartItem: CartItem,
        val onRemoveClicked: (CartItem) -> Unit,
        val onItemChecked: (CartItem, Boolean) -> Unit,
        val onItemAmountChanged: (CartItem, Int) -> Unit,
        val scope: CoroutineScope,
    ) : CartListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ListItemCartBinding.inflate(layoutInflater, root, false)
            val delegate
                get() =
                    adapterDelegateViewBinding<Item, CartListItem, ListItemCartBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            binding.tvName.text = item.cartItem.data.name
                            binding.tvPrice.text = itemView.context.getString(
                                R.string.price_placeholder,
                                item.cartItem.data.price
                            )
                            binding.ivIcon.setImageResource(R.drawable.product_img_example)

                            binding.cbSelected.isChecked = item.cartItem.isSelected
                            binding.cbSelected.setOnCheckedChangeListener { _, isChecked ->
                                item.onItemChecked(item.cartItem, isChecked)
                            }

                            binding.ibDelete.setOnClickListener {
                                item.onRemoveClicked(item.cartItem)
                            }

                            binding.productAmount.setAmount(item.cartItem.count)
                            binding.productAmount.amountFlow
                                .onEach { amount ->
                                    item.onItemAmountChanged(item.cartItem, amount)
                                }
                                .launchIn(item.scope)
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
