package com.narcissus.marketplace.ui.home.util

import android.graphics.Paint
import android.widget.TextView

fun TextView.crossOut() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}
