package com.android.galleryapp.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.galleryapp.R
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.ui.theme.SPACING_L
import com.android.galleryapp.ui.theme.SPACING_XXS
import com.android.galleryapp.ui.theme.Yellow
import com.android.galleryapp.utils.getVideoThumbnail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MediaItem(media: MediaFile, onClick: () -> Unit) {

    val context = LocalContext.current

    val isVideo = media.type == MediaType.VIDEO

    if(isVideo){
        media.thumbnail?.let {
            VideoGalleryItem(media.thumbnail, context = context, onClick = onClick)
        }
    }else{
        PhotoGalleryItem(media = media, onClick = onClick)
    }
}

@Composable
fun PhotoGalleryItem(media: MediaFile, onClick: () -> Unit) {
    val imagePath = if(media.type == MediaType.VIDEO) media.thumbnail else media.uri

    Image(
        painter = rememberAsyncImagePainter(imagePath),
        contentDescription = media.name,
        modifier = Modifier
            .padding(SPACING_XXS.dp)
            .background(Yellow.v20)
            .size(IMAGE_SIZE.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun VideoGalleryItem(videoUri: Uri, context: Context, onClick: () -> Unit) {
    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }

    // Fetch thumbnail asynchronously
    LaunchedEffect(videoUri) {
        thumbnail = withContext(Dispatchers.IO) { getVideoThumbnail(context, videoUri) }
    }

    Box(
        modifier = Modifier
            .padding(SPACING_XXS.dp)
            .background(Yellow.v20)
            .size(IMAGE_SIZE.dp)
            .clickable { onClick() }
    ) {
        // Thumbnail image
        if (thumbnail != null) {
            Image(
                bitmap = thumbnail!!.asImageBitmap(),
                contentDescription = stringResource(R.string.video_thumbnail),
                modifier = Modifier.fillMaxSize(), // Fill the available space
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = stringResource(R.string.video_thumbnail),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Play icon in the center
        Image(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(SPACING_L.dp) // Adjust the size of the play icon
        )
    }

}

fun getMimeType(context: Context, uri: Uri): String? {
    return context.contentResolver.getType(uri)
}

private const val IMAGE_SIZE = 100