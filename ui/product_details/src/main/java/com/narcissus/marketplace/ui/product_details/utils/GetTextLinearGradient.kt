package com.narcissus.marketplace.ui.product_details.utils

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import com.narcissus.marketplace.core.R as CORE

fun getTextLinearGradient(context: Context) = LinearGradient(
    0f,
    0f,
    100f,
    100f,
    arrayOf(
        context.getColor(CORE.color.gradient_background_start),
        context.getColor(CORE.color.gradient_background_end),
    ).toIntArray(),
    null,
    Shader.TileMode.CLAMP,
)
