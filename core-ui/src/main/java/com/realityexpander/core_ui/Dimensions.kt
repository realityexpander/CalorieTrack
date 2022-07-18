package com.realityexpander.core_ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 24.dp,
    val spaceExtraLarge: Dp = 32.dp,
    val spaceExtraExtraLarge: Dp = 48.dp,
)


// Allows for easy access to the dimensions of the app (like a global variable)
// prevents prop drilling and makes it easier to change the dimensions of the app
val LocalSpacing = compositionLocalOf {
    Dimensions()
}
