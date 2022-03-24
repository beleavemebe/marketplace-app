package com.narcissus.marketplace.ui.user.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h4 = TextStyle(
        fontFamily = MontserratSemiBold,
        fontSize = 34.sp,
    ),
    h5 = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 24.sp,
    ),
    h6 = TextStyle(
        fontFamily = MontserratSemiBold,
        fontSize = 21.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 17.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = MontserratMedium,
        fontSize = 15.sp,
    ),
    body1 = TextStyle(
        fontFamily = MontserratSemiBold,
        fontSize = 16.sp,
    ),
    body2 = TextStyle(
        fontFamily = Montserrat,
        fontSize = 14.sp,
    ),
    button = TextStyle(
        fontFamily = MontserratBold,
        fontSize = 14.sp,
    ),
    caption = TextStyle(
        fontFamily = Montserrat,
        fontSize = 12.sp,
    ),
    overline = TextStyle(
        fontFamily = Montserrat,
        fontSize = 10.sp,
    ),
)

val TextStyle.regular: TextStyle
    @Composable get() = copy(fontFamily = Montserrat)

val TextStyle.subtitleColor: TextStyle
    @Composable get() = copy(color = SubtitleColor)
