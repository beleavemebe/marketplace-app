package com.narcissus.marketplace.ui.user.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.narcissus.marketplace.domain.model.orders.Order
import com.narcissus.marketplace.domain.model.orders.OrderItem
import com.narcissus.marketplace.domain.model.orders.OrderStatus
import com.narcissus.marketplace.ui.user.theme.DarkTheme
import com.narcissus.marketplace.ui.user.theme.DefaultPadding
import com.narcissus.marketplace.ui.user.theme.HalfPadding
import com.narcissus.marketplace.ui.user.theme.LightTheme
import com.narcissus.marketplace.ui.user.theme.MontserratSemiBold
import com.narcissus.marketplace.ui.user.theme.Shapes
import com.narcissus.marketplace.ui.user.theme.SmallPadding
import java.util.Date
import com.narcissus.marketplace.core.R as CORE

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderCard(order: Order) {
    Column(
        modifier = Modifier
            .padding(horizontal = DefaultPadding, vertical = HalfPadding)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.onSecondary)
            .padding(horizontal = DefaultPadding, vertical = HalfPadding)
    ) {
        Spacer(modifier = Modifier.height(HalfPadding))

        Row(modifier = Modifier.fillMaxWidth()) {
            KeyValue(
                keyText = stringResource(id = CORE.string.order),
                valueText = stringResource(
                    id = CORE.string.order_number_placeholder,
                    formatArgs = arrayOf(order.number),
                ),
            )

            Text(
                text = stringResource(
                    id = CORE.string.price_placeholder,
                    formatArgs = arrayOf(order.summaryPrice),
                ),
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        }

        KeyValue(
            keyText = "Status",
            valueText = order.status.toString(),
        )

        Spacer(modifier = Modifier.height(SmallPadding / 2))

        KeyValue(
            keyText = "Items",
            valueText = "",
        )

        order.items.forEach {
            OrderItemEntry(it)
        }
    }
}

@Composable
fun OrderItemEntry(orderItem: OrderItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = SmallPadding),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(orderItem.productImage)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Order item ${orderItem.productName}",
            modifier = Modifier
                .size(30.dp)
                .clip(Shapes.small),
        )

        Spacer(modifier = Modifier.width(HalfPadding))

        Text(
            text = stringResource(
                id = CORE.string.amount_placeholder,
                formatArgs = arrayOf(orderItem.amount),
            ),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
        )

        Spacer(modifier = Modifier.width(SmallPadding))

        Text(
            text = orderItem.productName,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.fillMaxWidth(0.8f),
        )

        Text(
            text = stringResource(
                id = CORE.string.price_placeholder,
                formatArgs = arrayOf(orderItem.productPrice),
            ),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(),
        )

    }
}

@Composable
fun KeyValue(keyText: String, valueText: String) {
    Row {
        Text(
            text = keyText,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
        )

        Text(
            text = " ",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2,
        )

        Text(
            text = valueText
                .lowercase()
                .replaceFirstChar(Char::uppercase),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body2
                .copy(fontFamily = MontserratSemiBold),
        )
    }
}

@Preview
@Composable
fun OrderCardLight() {
    LightTheme {
        OrderCard(
            order = Order(
                "uuid",
                1234,
                Date(),
                OrderStatus.ACCEPTED,
                123,
                listOf(
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women Jeans",
                        1,
                        526,
                    ),
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women JeansSmall Women JeansSmall Women JeansSmall Women Jeans",
                        1,
                        526,
                    ),
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women Jeans",
                        1,
                        526,
                    ),
                ),
            ),
        )
    }
}


@Preview
@Composable
fun OrderCardDark() {
    DarkTheme {
        OrderCard(
            order = Order(
                "uuid",
                1234,
                Date(),
                OrderStatus.ACCEPTED,
                123,
                listOf(
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women Jeans",
                        1,
                        526,
                    ),
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women JeansSmall Women JeansSmall Women JeansSmall Women Jeans",
                        1,
                        526,
                    ),
                    OrderItem(
                        "65f116dd-358b-4cfb-8b65-1ea403e1ada4",
                        "https://api-narcissus-marketplace.herokuapp.com/pic/fashion_women/women_jeans_600.png",
                        526,
                        "Small Women Jeans",
                        1,
                        526,
                    ),
                ),
            ),
        )
    }
}
