package com.android.galleryapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColors = lightColors(
    primary = Charcoal.v100,
    primaryVariant = Charcoal.v60,
    onPrimary = Base.White,
    background = Base.White,
    secondary = Green.v100,
    onSecondary = Base.Black,
    error = Red.v120
)

@Composable
fun GalleryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColors,
        shapes = GalleryShapes,
        content = content
    )
}
