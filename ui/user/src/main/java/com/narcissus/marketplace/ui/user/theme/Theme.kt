package com.narcissus.marketplace.ui.user.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = GradientBackgroundEnd,
    primaryVariant = Color.Black,
    secondary = Purple200,
    error = Red,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = Color(0xFFECE6E6),
    onSecondary = Color(0xFFB6B5B5),
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorPalette = lightColors(
    primary = GradientBackgroundEnd,
    primaryVariant = Purple700,
    secondary = Color(0xFF8E44EB),
    error = Red,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.Black, // todo: fix palette
    onSecondary = LightGrey, // todo: fix palette
    onBackground = Color.Black,
    onSurface = Color.Black,
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
