package com.narcissus.marketplace.ui.product_details.model

import android.os.Parcelable
import com.narcissus.marketplace.domain.model.Review
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParcelableReview(
    val reviewId: String,
    val author: String,
    val details: String,
    val rating: Int,
    val reviewAuthorIcon: String,
) : Parcelable

fun Review.toParcelableReview() =
    ParcelableReview(
        reviewId,
        author,
        details,
        rating,
        reviewAuthorIcon,
    )
