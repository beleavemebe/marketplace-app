package com.narcissus.marketplace.ui.cart

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import com.narcissus.marketplace.R
import kotlin.math.max

class IncreaseDecreaseAmountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var ibDecrease: ImageButton
    private lateinit var ibIncrease: ImageButton
    private lateinit var tvAmount: TextView

    private var currentAmount = 1

    init {
        inflate(context, R.layout.view_increase_decrease_amount, this)
        orientation = HORIZONTAL
        background = AppCompatResources.getDrawable(context, R.drawable.rounded_shape)

        findViews()
        initListeners()
        refreshAmount()
    }

    private fun findViews() {
        tvAmount = findViewById(R.id.tvAmount)
        ibDecrease = findViewById(R.id.ibDecrease)
        ibIncrease = findViewById(R.id.ibIncrease)
    }

    private fun initListeners() {
        ibDecrease.setOnClickListener {
            currentAmount = max(1, currentAmount - 1)
            invalidate()
        }

        ibIncrease.setOnClickListener {
            currentAmount += 1
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        refreshAmount()
    }

    private fun refreshAmount() {
        tvAmount.text = currentAmount.toString()
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        return bundleOf("amount" to currentAmount)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(null)
        val restoredAmount = (state as? Bundle)?.get("amount") as? Int
        currentAmount = restoredAmount ?: 1
    }
}
