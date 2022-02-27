package com.narcissus.marketplace.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.model.CartItem

class CartViewHolder(
    private val binding: ListItemCartBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) = with(binding) {
        cartItemName.text = cartItem.data.name
        (cartItem.data.price.toString()+"$").also { cartItemPrice.text = it }
        cartItemProductAmount.text = cartItem.count.toString()
        cartItemIcon.setImageResource(R.drawable.product_img_example)
    }
}