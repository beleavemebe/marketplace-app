package com.narcissus.marketplace.ui.user.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.model.orders.OrderStatus
import com.narcissus.marketplace.ui.user.theme.DefaultPadding
import com.narcissus.marketplace.ui.user.theme.LightTheme
import java.util.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderCard(order: Order) {
    Column(
        modifier = Modifier.padding(horizontal = DefaultPadding)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = order.id,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onPrimary,
        )
        Text(
            text = order.date.toString(),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onPrimary,
        )
        Text(
            text = order.status.toString(),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onPrimary,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun OrderCardLight() {
    LightTheme {
        OrderCard(order = Order("", 1234, Date(), OrderStatus.ACCEPTED, 123, listOf()))
    }
}
