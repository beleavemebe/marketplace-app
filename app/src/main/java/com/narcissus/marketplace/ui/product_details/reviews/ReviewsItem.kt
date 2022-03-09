package com.narcissus.marketplace.ui.product_details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import coil.transform.RoundedCornersTransformation
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsReviewBinding
import com.narcissus.marketplace.model.Review

typealias ReviewBinding = ListItemDetailsReviewBinding

sealed class ReviewsItem {
    companion object {
        private const val REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS = 12f

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewsItem>() {
            override fun areItemsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem
            ): Boolean =
                (newItem as ReviewItem).review.reviewId == (oldItem as ReviewItem).review.reviewId

            override fun areContentsTheSame(
                oldItem: ReviewsItem,
                newItem: ReviewsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ReviewItem(val review: Review) : ReviewsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ReviewBinding.inflate(layoutInflater, root, false)

            val delegate get() =
                adapterDelegateViewBinding<ReviewItem, ReviewsItem, ReviewBinding>(
                    ::inflateBinding
                ) {
                    bind {
                        binding.tvReviewsAuthor.text = item.review.author
                        binding.tvReviewsDescription.text = item.review.details
                        binding.reviewsRatingBar.progress = item.review.rating
                        binding.ivReviewsAvatar.load(item.review.reviewAuthorIcon) {
                            transformations(
                                RoundedCornersTransformation(
                                    REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS
                                )
                            )
                        }
                    }
                }
        }
    }
}
