package com.narcissus.marketplace.ui.product_details.reviews

import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ListItemDetailsReviewBinding
import com.narcissus.marketplace.model.Review

class ReviewViewHolder(
    private val binding: ListItemDetailsReviewBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(review: Review) = with(binding) {
        binding.authorTV.text = review.author
        binding.reviewRatingBar.rating = review.rating.toFloat()
        binding.reviewTextTV.text = review.details
    }
}
