package com.narcissus.marketplace.ui.user.orders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.narcissus.marketplace.ui.user.theme.DarkTheme
import com.narcissus.marketplace.ui.user.theme.LightTheme
import com.narcissus.marketplace.ui.user.theme.Montserrat
import com.narcissus.marketplace.ui.user.theme.regular

private const val ASSET_NO_ORDERS_YET = "no_orders_yet.json"

@Composable
fun NoOrdersYet() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.h5.regular,
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.15f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            val composition by rememberLottieComposition(
                LottieCompositionSpec.Asset(ASSET_NO_ORDERS_YET)
            )

            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = Int.MAX_VALUE,
            )

            LottieAnimation(composition, progress)
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.25f))

        Text(
            text = "No orders yet",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6
                .copy(fontFamily = Montserrat),
        )

        Spacer(modifier = Modifier.fillMaxHeight())
    }
}

@Preview
@Composable
fun NoOrdersYetPreviewLight() {
    LightTheme {
        NoOrdersYet()
    }
}

@Preview
@Composable
fun NoOrdersYetPreviewDark() {
    DarkTheme {
        NoOrdersYet()
    }
}
