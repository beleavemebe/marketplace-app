package com.narcissus.marketplace.ui.user.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.usecase.GetOrderList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class OrdersViewModel(
    getOrderList: GetOrderList,
) : ViewModel(), ContainerHost<OrdersState, OrdersSideEffect> {

    override val container: Container<OrdersState, OrdersSideEffect> =
        container(OrdersState(isLoading = true))

    init {
        getOrderList()
            .onEach(::updateScreenState)
            .launchIn(viewModelScope)
    }

    private fun updateScreenState(orders: List<Order>) = intent {
        reduce {
            OrdersState(
                isLoading = false,
                orders = orders,
            )
        }
    }

    fun navigateUp() = intent {
        postSideEffect(OrdersSideEffect.NavigateUp)
    }
}
