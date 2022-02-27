package com.narcissus.marketplace.ui.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ListItemProductPreviewBinding
import com.narcissus.marketplace.model.ProductPreview

class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>() {
    private var items = mutableListOf<ProductPreview>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitItems(list: List<ProductPreview>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val context = LayoutInflater.from(parent.context)
        val binding = ListItemProductPreviewBinding.inflate(context, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}
