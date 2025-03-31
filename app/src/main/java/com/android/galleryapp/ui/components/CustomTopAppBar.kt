package com.android.galleryapp.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.android.galleryapp.ui.theme.Green

@Composable
fun CustomTopAppBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon?.let {
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = it, contentDescription = "Navigation Icon")
                }
            }
        },
        actions = actions,
        backgroundColor = Green.v80
    )
}
