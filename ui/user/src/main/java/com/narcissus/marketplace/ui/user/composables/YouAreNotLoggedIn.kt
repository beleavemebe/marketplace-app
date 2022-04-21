package com.narcissus.marketplace.ui.user.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.narcissus.marketplace.ui.user.theme.DarkTheme
import com.narcissus.marketplace.ui.user.theme.DefaultPadding
import com.narcissus.marketplace.ui.user.theme.GradientBackgroundEnd
import com.narcissus.marketplace.ui.user.theme.GradientBackgroundStart
import com.narcissus.marketplace.ui.user.theme.LightTheme
import com.narcissus.marketplace.ui.user.theme.Montserrat
import com.narcissus.marketplace.ui.user.theme.White
import com.narcissus.marketplace.ui.user.theme.regular

private const val ASSET_YOU_ARE_NOT_LOGGED_IN = "you_are_not_logged_in_3d.json"

@Composable
fun YouAreNotLoggedIn(onSignInClicked: () -> Unit) {
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
                LottieCompositionSpec.Asset(ASSET_YOU_ARE_NOT_LOGGED_IN)
            )

            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = Int.MAX_VALUE,
            )

            LottieAnimation(composition, progress)
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.25f))

        Text(
            text = "You are not logged in",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6
                .copy(fontFamily = Montserrat),
        )

        Spacer(modifier = Modifier.height(DefaultPadding * 4))

        Button(
            onClick = onSignInClicked,
            elevation = ButtonDefaults.elevation(0.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = DefaultPadding * 2)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    Brush.horizontalGradient(
                        listOf(GradientBackgroundStart, GradientBackgroundEnd)
                    ),
                ),
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.button
                    .copy(color = White)
            )
        }
    }
}

@Composable
@Preview
fun YouAreNotLoggedInPreviewLight() {
    LightTheme {
        YouAreNotLoggedIn {}
    }
}

@Composable
@Preview
fun YouAreNotLoggedInPreviewDark() {
    DarkTheme {
        YouAreNotLoggedIn {}
    }
}
