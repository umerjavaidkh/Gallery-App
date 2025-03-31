package com.android.galleryapp.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.galleryapp.ui.components.MediaItem
import com.android.galleryapp.ui.components.getMimeType
import com.android.galleryapp.viewmodel.SharedGalleryViewModel

@Composable
fun DetailScreen(albumName: String, viewModel: SharedGalleryViewModel){
    val context = LocalContext.current

    val albumMedia = viewModel.getMediaFilesForAlbum(albumName)

    if(!albumMedia.isNullOrEmpty()){
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
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
    }else {
        EmptyMediaState()
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
            contentDescription = "No Media",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No media found for the current album.",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}