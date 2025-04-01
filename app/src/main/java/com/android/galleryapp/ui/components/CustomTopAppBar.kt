package com.android.galleryapp.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.galleryapp.ui.theme.Base.White
import com.android.galleryapp.ui.theme.BrandingOrange
import com.android.galleryapp.ui.theme.GalleryTypography
import com.android.galleryapp.ui.theme.Green

@Composable
fun CustomTopAppBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, style = GalleryTypography.Heading.h2.copy(color = White)) },
        navigationIcon = navigationIcon?.let {
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = it, contentDescription = "Navigation Icon")
                }
            }
        },
        actions = actions,
        backgroundColor = BrandingOrange.v100
    )
}
