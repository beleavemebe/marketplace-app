package com.narcissus.marketplace.ui.user.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.narcissus.marketplace.ui.user.theme.DarkTheme
import com.narcissus.marketplace.ui.user.theme.LightTheme

private const val ASSET_CIRCULAR_PROGRESS = "circular_progress.json"

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset(ASSET_CIRCULAR_PROGRESS),
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = Int.MAX_VALUE,
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp),
        )
    }
}

@Composable
@Preview
fun LoadingPreviewLight() {
    LightTheme {
        Loading()
    }
}

@Composable
@Preview
fun LoadingPreviewDark() {
    DarkTheme {
        Loading()
    }
}
