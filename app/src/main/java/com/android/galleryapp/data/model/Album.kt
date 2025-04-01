package com.android.galleryapp.data.model

data class Album(
    val name: String,
    var media: List<MediaFile>? = null
)