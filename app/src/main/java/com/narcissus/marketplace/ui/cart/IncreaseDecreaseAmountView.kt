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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.max

class IncreaseDecreaseAmountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var ibDecrease: ImageButton
    private lateinit var ibIncrease: ImageButton
    private lateinit var tvAmount: TextView

    fun setAmount(value: Int) {
        _amountFlow.value = value
    }

    private val _amountFlow = MutableStateFlow(1)
    val amountFlow = _amountFlow.asStateFlow()
    var maxAmount: Int = 0
    private val _boundaryAmountReachedTriggerFlow = MutableSharedFlow<Boolean>()
    val boundaryAmountReachedTriggerFlow = _boundaryAmountReachedTriggerFlow.asSharedFlow()
    private var viewScope: CoroutineScope? = null

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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        observeBoundaryAmountReachedFlow()
    }

    private fun initListeners() {
        ibDecrease.setOnClickListener {
            if (amountFlow.value == MIN_AMOUNT) {
                callBoundaryReachedAnimation()
            } else {
                _amountFlow.value = max(MIN_AMOUNT, _amountFlow.value - 1)
                invalidate()
            }
        }
        ibIncrease.setOnClickListener {
            if (maxAmount == _amountFlow.value) {
                callBoundaryReachedAnimation()
            } else {
                _amountFlow.value += 1
                invalidate()
            }
        }
    }

    private fun callBoundaryReachedAnimation() {
        viewScope?.launch { _boundaryAmountReachedTriggerFlow.emit(true) }
    }

    private fun observeBoundaryAmountReachedFlow() {
        viewScope?.let {
            amountFlow.onEach { currentAmount ->
                when (currentAmount) {
                    1 -> ibDecrease.alpha = TRANSLUCENT_ALPHA
                    else -> ibDecrease.alpha = DENSE_ALPHA
                }
                when (currentAmount) {
                    maxAmount -> ibIncrease.alpha = TRANSLUCENT_ALPHA
                    else -> ibIncrease.alpha = DENSE_ALPHA
                }
            }.launchIn(it)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        refreshAmount()
    }

    private fun refreshAmount() {
        tvAmount.text = _amountFlow.value.toString()
    }

    override fun onSaveInstanceState(): Parcelable {
        super.onSaveInstanceState()
        return bundleOf(ARG_AMOUNT to _amountFlow.value)
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(null)
        val restoredAmount = (state as? Bundle)?.get(ARG_AMOUNT) as? Int
        _amountFlow.value = restoredAmount ?: 1
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewScope?.cancel()
        viewScope = null
    }

    companion object {
        const val ARG_AMOUNT = "amount"
        const val MIN_AMOUNT = 1
        const val TRANSLUCENT_ALPHA = 0.3f
        const val DENSE_ALPHA = 1f
    }
}
