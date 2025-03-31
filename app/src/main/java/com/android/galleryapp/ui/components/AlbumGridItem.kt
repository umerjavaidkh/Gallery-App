package com.android.galleryapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.R
import com.android.galleryapp.ui.theme.*

@Composable
fun AlbumGridItem(album: Album, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val imagePainter = rememberAsyncImagePainter(
        model = album.lastImageUri?.toString(), // Ensure it's a proper URI
        error = painterResource(R.drawable.placeholder), // Add a fallback
        placeholder = painterResource(R.drawable.placeholder)
    )

    Box(
        modifier = modifier
            .aspectRatio(1f) // Square shape
            .clip(RoundedCornerShape(1.dp))
            .background(Grey.v20).clickable { onClick() }
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
                        startY = 200f
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
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Album Icon",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = album.name,
                style = GalleryTypography.Body.S
            )
        }

        // Media Count (Top-Right)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = (album.media?.size ?: 0).toString(),
                style = GalleryTypography.Body.M
            )
        }
    }
}