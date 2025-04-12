package com.android.galleryapp.viewmodel.uistate

import com.android.galleryapp.data.model.Album

sealed class AlbumUiState {
    object Loading : AlbumUiState()
    data class Success(val albums: List<Album>) : AlbumUiState()
    data class Error(val message: String) : AlbumUiState()
}