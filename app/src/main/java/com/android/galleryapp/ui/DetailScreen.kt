package com.android.galleryapp.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.galleryapp.R
import com.android.galleryapp.ui.components.CustomTopAppBar
import com.android.galleryapp.ui.components.MediaItem
import com.android.galleryapp.ui.components.getMimeType
import com.android.galleryapp.ui.theme.BrandingOrange
import com.android.galleryapp.ui.theme.GalleryTypography
import com.android.galleryapp.ui.theme.SPACING_M
import com.android.galleryapp.viewmodel.SharedGalleryViewModel

@Composable
fun DetailScreen(albumName: String, viewModel: SharedGalleryViewModel){
    val context = LocalContext.current

    val albumMedia = viewModel.getMediaFilesForAlbum(albumName)

    Scaffold(
        backgroundColor =  BrandingOrange.v60,
        topBar = { CustomTopAppBar(title = albumName,
            navigationIcon = Icons.Default.ArrowBack,
            onNavigationClick = viewModel::goBack) },
    ) { paddingValues ->

        if (!albumMedia.isNullOrEmpty()) {
            LazyVerticalGrid(columns = GridCells.Fixed(GRID_COLUMNS), contentPadding = paddingValues) {
                items(albumMedia) { media ->
                    MediaItem(media) {

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(media.uri, getMimeType(context, media.uri))
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        } else {
            EmptyMediaState()
        }
    }
}

@Composable
private fun EmptyMediaState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Optional: Add an image or icon
        Icon(
            imageVector = Icons.Default.Build,
            contentDescription = stringResource(R.string.no_media),
            modifier = Modifier.size(ICON_SIZE.dp),
            tint = MaterialTheme.colors.onSurface.copy(alpha = ICON_ALPHA)
        )
        Spacer(modifier = Modifier.height(SPACING_M.dp))
        Text(
            text = stringResource(R.string.no_media_message),
            style = GalleryTypography.Heading.h3
        )
    }
}

private const val GRID_COLUMNS = 3
private const val ICON_ALPHA = 0.3f
private const val ICON_SIZE = 100