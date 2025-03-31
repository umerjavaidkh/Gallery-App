package com.android.galleryapp.data.util

fun shouldExcludeFile(path: String): Boolean {
    val lowerPath = path.lowercase()
    return lowerPath.contains("/cache/") ||  // Exclude cache
            lowerPath.contains("/thumbnails/") ||  // Exclude thumbnails
            lowerPath.contains(".nomedia")  // Exclude .nomedia files
}