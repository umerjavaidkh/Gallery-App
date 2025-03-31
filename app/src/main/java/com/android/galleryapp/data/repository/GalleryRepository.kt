package com.android.galleryapp.data.repository

import com.android.galleryapp.data.model.Album
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    suspend fun fetchAlbums(): Flow<List<Album>>
}