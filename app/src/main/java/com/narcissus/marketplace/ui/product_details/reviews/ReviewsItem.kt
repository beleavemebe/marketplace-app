package com.narcissus.marketplace.ui.product_details.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsReviewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsReviewLoadingBinding
import com.narcissus.marketplace.model.Review

typealias ReviewBinding = ListItemDetailsReviewBinding
typealias LoadingBinding = ListItemDetailsReviewLoadingBinding

sealed class ReviewsItem {
    class ReviewItem(val review: Review) : ReviewsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = ReviewBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<ReviewItem, ReviewsItem, ReviewBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            binding.authorTV.text = item.review.author
                            binding.reviewTextTV.text = item.review.details
                            binding.reviewRatingBar.progress = item.review.rating
                        }
                    }
        }
    }

    class LoadingItem : ReviewsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = LoadingBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<LoadingItem, ReviewsItem, LoadingBinding>(
                        ::inflateBinding
                    ) {}
        }
    }

}
