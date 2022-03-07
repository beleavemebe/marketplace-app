package com.narcissus.marketplace.ui.catalog

import android.graphics.*
import androidx.core.graphics.applyCanvas
import coil.bitmap.BitmapPool
import coil.size.Size
import coil.transform.Transformation

class CoilGradientTransformation() : Transformation {

    override fun key(): String = CoilGradientTransformation::class.java.name

    override suspend fun transform(pool: BitmapPool, input: Bitmap, size: Size): Bitmap {

        val width = input.width
        val height = input.height
        val centerX = width*0.75f
        val centerY = 0f
        val radius = width*0.7f
        val gradient = RadialGradient(
            centerX, centerY, radius, Color.TRANSPARENT, Color.BLACK,
            Shader.TileMode.CLAMP
        )
        val config = input.config
        val output = pool.get(width, height, config)
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val paintRect = Paint().apply {
            isAntiAlias = true
            shader = gradient
            color = Color.BLACK
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        }
        output.applyCanvas {
            drawBitmap(input, 0f, 0f, paintBitmap)
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintRect)
        }
        pool.put(input)
        return output
    }
}