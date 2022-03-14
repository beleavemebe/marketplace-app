package com.narcissus.marketplace.ui.product_details.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ReviewParcelable(
    val reviewId: String,
    val author: String,
    val details: String,
    val rating: Int,
    val reviewAuthorIcon: String,
) : Parcelable
