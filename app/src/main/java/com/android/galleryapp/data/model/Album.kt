package com.android.galleryapp.data.model

import android.net.Uri

data class Album(
    val name: String,
    var lastImageUri: Uri?,
    var media: List<MediaFile>? = null
)