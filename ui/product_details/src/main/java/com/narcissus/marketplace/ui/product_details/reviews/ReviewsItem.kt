package com.narcissus.marketplace.ui.product_details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import coil.transform.CircleCropTransformation
import com.narcissus.marketplace.ui.product_details.databinding.ListItemDetailsReviewBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.ui.product_details.model.ParcelableReview

typealias ReviewBinding = ListItemDetailsReviewBinding

sealed class ReviewsItem {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewsItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem,
            ): Boolean =
                (newItem as ReviewItem).review.reviewId == (oldItem as ReviewItem).review.reviewId

            override fun areContentsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ReviewItem(val review: ParcelableReview) : ReviewsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ReviewBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<ReviewItem, ReviewsItem, ReviewBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvReviewPreviewAuthor.text = item.review.author
                        binding.tvReviewPreviewDescription.text = item.review.details
                        binding.reviewPreviewRatingBar.progress = item.review.rating
                        binding.ivReviewPreviewAvatar.load(item.review.reviewAuthorIcon) {
                            transformations(CircleCropTransformation())
                        }
                    }
                }
        }
    }
}
