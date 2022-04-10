package com.narcissus.marketplace.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.ValidateCard
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CheckoutViewModel(
    getCheckout: GetCheckout,
    getCartCost: GetCartCost,
    private val validateCard: ValidateCard
) : ViewModel() {

    val getCheckoutFlow = getCheckout()

    val getTotalFlow = getCartCost()

    private val _cardValidateFlow = MutableSharedFlow<CardValidateResult>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val cardValidateFlow = _cardValidateFlow.asSharedFlow()

    fun checkCard(cardHolder:String, cardNumber:String, cardExpireDate:String, cardCvv:String){
        viewModelScope.launch {
            val validateResult = validateCard(cardHolder,cardNumber,cardExpireDate,cardCvv)
            _cardValidateFlow.emit(validateResult)
        }
    }

}
