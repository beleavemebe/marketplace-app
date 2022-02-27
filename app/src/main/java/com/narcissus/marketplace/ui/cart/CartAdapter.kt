package com.narcissus.marketplace.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ListItemCartBinding
import com.narcissus.marketplace.model.CartItem

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {
    private var items = mutableListOf<CartItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val context = LayoutInflater.from(parent.context)
        val binding = ListItemCartBinding.inflate(context, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<CartItem>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    //TO DO add DiffUtil and some features
}
