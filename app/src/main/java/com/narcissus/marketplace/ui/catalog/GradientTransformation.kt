package com.narcissus.marketplace.ui.catalog


import android.graphics.*
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.transform.Transformation

class GradientTransformation : Transformation {

    override val cacheKey: String = javaClass.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return input
        // TODO: use linear, lighter gradient
        val width = input.width
        val height = input.height
        val centerX = width * 0.75f
        val centerY = 0f
        val radius = width * 0.7f
        val gradient = RadialGradient(
            centerX, centerY, radius, Color.TRANSPARENT, Color.BLACK,
            Shader.TileMode.CLAMP
        )
        val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        val paintRect = Paint().apply {
            isAntiAlias = true
            shader = gradient
            color = Color.BLACK
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        }
        val output = createBitmap(width, height, input.config)
        output.applyCanvas {
            drawBitmap(input, 0f, 0f, paintBitmap)
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintRect)
        }
        return output
    }
}
