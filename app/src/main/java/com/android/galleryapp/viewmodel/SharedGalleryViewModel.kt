package com.android.galleryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.repository.GalleryRepository
import com.android.galleryapp.navigation.Destination
import com.android.galleryapp.navigation.Navigator
import com.android.galleryapp.viewmodel.uistate.AlbumUiState
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

    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

    fun fetchAlbums() {
        viewModelScope.launch {
            _uiState.value = AlbumUiState.Loading
            try {
                repository.fetchAlbums()
                    .collect { albums ->
                        _uiState.value = AlbumUiState.Success(albums)
                    }
            }catch (e: Exception){
                _uiState.value = AlbumUiState.Error(e.message ?: "")
            }
        }
    }

    fun getMediaFilesForAlbum(albumId: String): List<MediaFile> {
        return (uiState.value as? AlbumUiState.Success)
            ?.albums
            ?.find { it.name == albumId }
            ?.media
            ?: emptyList()
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

    // We can have app wise error screen or snack bar
    fun showErrorOrMessage(text: String){

    }
}