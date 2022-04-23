package com.github.beleavemebe.ui.cart.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.narcissus.marketplace.domain.card.CardValidationResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import com.github.beleavemebe.ui.cart.di.CartQualifiers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
import java.util.UUID

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard,
) : ViewModel(), KoinComponent {

    val orderId = UUID.randomUUID().toString()

    private val _cardValidationResultFlow = MutableSharedFlow<CardValidationResult>()
    val cardValidationResultFlow = _cardValidationResultFlow.asSharedFlow()

    private val _screenStateFlow = MutableStateFlow<CheckoutScreenState>(CheckoutScreenState.Loading)
    val screenStateFlow = _screenStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val items = getCheckout()
            val totalCost = getCartCost()
            _screenStateFlow.emit(
                CheckoutScreenState.Idle(items, totalCost),
            )
        }
    }

    fun validateCard(
        cardHolder: String,
        cardNumber: String,
        expirationDate: String,
        cvv: String,
    ) {
        viewModelScope.launch {
            val validateResult = validateCard.invoke(cardHolder, cardNumber, expirationDate, cvv)
            _cardValidationResultFlow.emit(validateResult)
        }
    }

    private val workManager: WorkManager by inject()

    private val paymentWorkData: Data by inject(
        qualifier<CartQualifiers.PaymentWorkerInputData>()
    ) {
        parametersOf(orderId)
    }

    private val paymentWorkRequest: OneTimeWorkRequest by inject(
        qualifier<CartQualifiers.PaymentOneTimeRequest>()
    ) {

        parametersOf(paymentWorkData)
    }

    fun proceedWithOrderPlacement() {
        workManager.enqueue(paymentWorkRequest)
        workManager.getWorkInfoByIdLiveData(paymentWorkRequest.id)
            .observeForever { workInfo: WorkInfo? ->
                if (workInfo == null) return@observeForever
                onPaymentWorkCompleted(workInfo)
            }
    }

    private fun onPaymentWorkCompleted(workInfo: WorkInfo) {
        when (workInfo.state) {
            WorkInfo.State.RUNNING -> {
                onPaymentInProgress()
            }
            WorkInfo.State.SUCCEEDED -> {
                val msg = workInfo.outputData.getString(orderId) ?: return
                onPaymentSuccessful(msg)
            }
            else -> {
                val msg = workInfo.outputData.getString(orderId) ?: return
                onPaymentFailed(msg)
            }
        }
    }

    private fun onPaymentSuccessful(msg: String) {
        _screenStateFlow.value = CheckoutScreenState.PaymentSuccessful(msg)
    }

    private fun onPaymentFailed(msg: String) {
        _screenStateFlow.value = CheckoutScreenState.PaymentFailed(msg)
    }

    private fun onPaymentInProgress() {
        _screenStateFlow.value = CheckoutScreenState.Loading
    }
}
