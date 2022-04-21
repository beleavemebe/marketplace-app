package com.narcissus.marketplace.ui.user.orders

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.model.orders.OrderPaymentResult
import com.narcissus.marketplace.domain.repository.OrderRepository
import com.narcissus.marketplace.domain.usecase.GetOrderList
import com.narcissus.marketplace.ui.user.theme.LightTheme
import com.narcissus.marketplace.ui.user.theme.regular
import kotlinx.coroutines.flow.Flow
import com.narcissus.marketplace.core.R as CORE

@Composable
fun OrdersScreen(viewModel: OrdersViewModel, orders: List<Order>) {
    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = { viewModel.navigateUp() }
                ) {
                    Icon(
                        painter = painterResource(CORE.drawable.ic_arrow_back),
                        contentDescription = "Navigate up",
                    )
                }
            },
            title = {
                Text(
                    text = "Orders",
                    style = MaterialTheme.typography.h5.regular,
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
        )

        Box {
            LazyColumn {
                items(orders.size) {
                    val order = orders[it]
                    OrderCard(order)
                }
            }
        }
    }
}
