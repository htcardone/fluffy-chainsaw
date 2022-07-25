package com.podium.technicalchallenge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DefaultColorPalette = darkColors(
    primary = Pink500,
    primaryVariant = Pink700,
    secondary = Purple200,
    background = DarkerBlue,
    surface = DarkerBlue,
    onBackground = Color.White,
    onSurface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DefaultColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}