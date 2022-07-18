package com.realityexpander.calorietrack.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.Typography
import androidx.compose.material.contentColorFor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun TypographyWithTextColor(colors: Colors): Typography {
    val newType = Typography(
        body1 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        button = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        caption = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        h1 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        h2 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        h3 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        h4 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
        h5 = Typography.h1.copy(color = colors.contentColorFor(colors.background)),
    )

    return newType
}

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
)