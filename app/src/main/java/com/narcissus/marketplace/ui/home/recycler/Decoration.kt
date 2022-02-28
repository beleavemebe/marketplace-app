package com.narcissus.marketplace.ui.home.recycler

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ExtraHorizontalMarginDecoration(
    private val margin: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val lastIndex = parent.adapter?.itemCount?.minus(1) ?: -1
        val marginDp = margin.toDp(parent.context)
        if (position == 0) {
            outRect.left = marginDp
        } else if (position == lastIndex) {
            outRect.right = marginDp
        }
    }
}

class ExtraVerticalMarginDecoration(
    private val margin: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val lastIndex = parent.adapter?.itemCount?.minus(1) ?: -1
        val marginDp = margin.toDp(parent.context)
        if (position == 0) {
            outRect.top = marginDp
        } else if (position == lastIndex) {
            outRect.bottom = marginDp
        }
    }
}

private fun Int.toDp(context: Context): Int {
    val density: Float = context.resources.displayMetrics.density
    return (this * density).toInt()
}
