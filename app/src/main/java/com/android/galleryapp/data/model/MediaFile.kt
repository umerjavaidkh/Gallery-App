package com.android.galleryapp.data.model

import android.net.Uri

data class MediaFile(
    val id: Long,
    val uri: Uri,
    val name: String,
    val path: String,
    val type: MediaType,
    val thumbnail: Uri? = null,
)

enum class MediaType {
    IMAGE, VIDEO
}
