package com.android.galleryapp.utils

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun getVideoThumbnail(context: Context, videoUri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android 10 (Q) and above
            val contentResolver = context.contentResolver
            contentResolver.loadThumbnail(videoUri, android.util.Size(IMAGE_SIZE, IMAGE_SIZE), null)
        } else {
            // For older versions (Android 9 and below)
            val videoId = ContentUris.parseId(videoUri)
            MediaStore.Video.Thumbnails.getThumbnail(
                context.contentResolver,
                videoId,
                MediaStore.Video.Thumbnails.MINI_KIND,
                null
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private const val IMAGE_SIZE = 100