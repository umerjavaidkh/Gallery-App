package com.android.galleryapp.ui.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.galleryapp.R
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.ui.theme.*
import com.android.galleryapp.ui.theme.Base.White
import com.android.galleryapp.utils.getVideoThumbnail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AlbumGridItem(album: Album, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val isVideo = album.media?.first()?.type == MediaType.VIDEO

    Content(album = album, isVideo = isVideo, modifier = modifier, onClick = onClick)
}

@Composable
fun Content(album: Album, isVideo: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit){

    val context = LocalContext.current

    val finalUrl = album.media?.first()?.uri ?: Uri.EMPTY

    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }

    if(isVideo) {
        // Fetch thumbnail asynchronously
        LaunchedEffect(finalUrl) {
            val thumbnailUri = album.media?.first()?.thumbnail ?: finalUrl
            thumbnail = withContext(Dispatchers.IO) { getVideoThumbnail(context, thumbnailUri) }
        }
    }

    val imagePainter = rememberAsyncImagePainter(
        model = if(isVideo) thumbnail else finalUrl,
        error = painterResource(R.drawable.placeholder), // Add a fallback
        placeholder = painterResource(R.drawable.placeholder),
        onError = {
                error ->  println(error)
        }
    )

    Box(
        modifier = modifier
            .aspectRatio(1f) // Square shape
            .clip(RoundedCornerShape(1.dp))
            .background(Grey.v20)
            .clickable { onClick() }
    ) {

        // Album Thumbnail
        Image(
            painter = imagePainter,
            contentDescription = album.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Charcoal.v60),
                        startY = START_XY
                    )
                )
        )

        // Album Name & Count
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(SPACING_S.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = mapNameToIcon(album.name)),
                contentDescription = stringResource(R.string.album_icon),
                modifier = Modifier.size(SPACING_L.dp)
            )
            Spacer(modifier = Modifier.width(SPACING_XS.dp))
            Text(
                text = album.name,
                style = GalleryTypography.Body.S.copy(White)
            )
        }

        // Media Count (Top-Right)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(SPACING_S.dp)
                .background(
                    BrandingOrange.v100.copy(alpha = COUNT_BG_ALPHA),
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = HORIZONTAL_PADDING.dp, vertical = SPACING_XXS.dp)
        ) {
            Text(
                text = (album.media?.size ?: 0).toString(),
                style = GalleryTypography.Body.M.copy(White)
            )
        }
    }

}

private fun mapNameToIcon(albumName: String): Int {
    return when {
        albumName.lowercase().contains("whatsapp", ignoreCase = true) -> R.drawable.ic_whatsapp
        albumName.lowercase().contains("camera", ignoreCase = true) -> R.drawable.ic_camera
        albumName.lowercase().contains("video", ignoreCase = true) -> R.drawable.ic_video
        albumName.lowercase().contains("movie", ignoreCase = true) -> R.drawable.ic_video
        else -> R.drawable.ic_picture
    }
}

private const val COUNT_BG_ALPHA = 0.7f
private const val HORIZONTAL_PADDING = 6
private const val START_XY = 200f