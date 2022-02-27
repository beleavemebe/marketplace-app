package com.narcissus.marketplace.ui.product_details.reviews

import androidx.recyclerview.widget.DiffUtil
import com.narcissus.marketplace.model.Review

class ReviewsDiffUtilCallback(
    private val oldList: List<Review>,
    private val newList: List<Review>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].reviewId == newList[newItemPosition].reviewId &&
                oldList[oldItemPosition].author == newList[newItemPosition].author &&
                oldList[oldItemPosition].details == newList[newItemPosition].details &&
                oldList[oldItemPosition].rating == newList[newItemPosition].rating

}
