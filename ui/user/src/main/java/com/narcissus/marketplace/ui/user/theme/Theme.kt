package com.narcissus.marketplace.ui.user.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Primary,
    secondary = Purple200,
    background = Black,
    surface = Black,
    error = Red,
    onPrimary = White,
    onSecondary = DarkPrimary,
    onSurface = White,
    onBackground = White,
    onError = White,
    secondaryVariant = White
)

private val LightColorPalette = lightColors(
    primary = Primary,
    secondary = Purple200,
    background = White,
    surface = White,
    error = Red,
    onPrimary = Black,
    onSecondary = GreyLight,
    onSurface = Black,
    onBackground = Black,
    onError = Black,
    secondaryVariant = Grey
)

@Composable
fun DefaultTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun LightTheme(
    content: @Composable () -> Unit
) {
    DefaultTheme(darkTheme = false) {
        content()
    }
}

@Composable
fun DarkTheme(
    content: @Composable () -> Unit
) {
    DefaultTheme(darkTheme = true) {
        content()
    }
}
