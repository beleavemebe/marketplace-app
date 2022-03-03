package com.narcissus.marketplace.ui.product_details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ListItemDetailsReviewBinding
import com.narcissus.marketplace.model.Review

class ReviewsAdapter : RecyclerView.Adapter<ReviewViewHolder>() {
    private var items = mutableListOf<Review>()

    fun submitItems(list: List<Review>) {
        val diffUtilCallback = ReviewsDiffUtilCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val context = LayoutInflater.from(parent.context)
        val binding = ListItemDetailsReviewBinding.inflate(context, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}
