package com.narcissus.marketplace.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.model.CartItem

class CartAdapter : RecyclerView.Adapter<CartItemViewHolder>() {
    private var items = mutableListOf<CartItem>()
    private var isSelected: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val context = LayoutInflater.from(parent.context)
        val binding = ListItemCartBinding.inflate(context, parent, false)
        return CartItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item,isSelected)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CartItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAll() {
        isSelected = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun unselectAll(){
        isSelected = false
        notifyDataSetChanged()
    }

    //TODO add DiffUtil and some features
}
