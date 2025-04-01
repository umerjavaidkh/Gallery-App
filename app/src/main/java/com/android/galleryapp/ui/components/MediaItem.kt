package com.android.galleryapp.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType

@Composable
fun MediaItem(media: MediaFile, onClick: () -> Unit) {
    val imagePath = if(media.type == MediaType.VIDEO) media.thumbnail else media.uri

    Image(
        painter = rememberAsyncImagePainter(imagePath),
        contentDescription = media.name,
        modifier = Modifier
            .padding(4.dp)
            .size(100.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

fun getMimeType(context: Context, uri: Uri): String? {
    return context.contentResolver.getType(uri)
}