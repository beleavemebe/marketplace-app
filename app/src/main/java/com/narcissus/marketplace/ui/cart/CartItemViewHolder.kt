package com.narcissus.marketplace.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.model.CartItem

class CartItemViewHolder(
    private val binding: ListItemCartBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) = with(binding) {
        tvName.text = cartItem.data.name
        tvPrice.text = itemView.context.getString(R.string.price_placeholder, cartItem.data.price)
//        tvAmount.text = cartItem.count.toString() // TODO set this amount in custom view attribute
        ivIcon.setImageResource(R.drawable.product_img_example)
    }
}
