package com.android.galleryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.repository.GalleryRepository
import com.android.galleryapp.navigation.Destination
import com.android.galleryapp.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedGalleryViewModel @Inject constructor(
    private val repository: GalleryRepository,
    private val navigator: Navigator
) : ViewModel()  {

    private val _albumsFlow = MutableStateFlow<List<Album>>(emptyList()) // StateFlow for UI
    val albumsFlow: StateFlow<List<Album>> = _albumsFlow.asStateFlow()

    fun fetchAlbums() {
        viewModelScope.launch {
            repository.fetchAlbums()
                .collect { album ->
                    _albumsFlow.value = album
                }
        }
    }

    fun getMediaFilesForAlbum(albumId: String): List<MediaFile>? {
        return albumsFlow.value.find { it.name == albumId }?.media
    }

    fun openDetailScreen(album: Album){
        viewModelScope.launch {
            navigator.navigate(destination = Destination.DetailScreen(album.name))
        }
    }

    fun goBack(){
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }

    fun showErrorOrMessage(text: String){

    }
}