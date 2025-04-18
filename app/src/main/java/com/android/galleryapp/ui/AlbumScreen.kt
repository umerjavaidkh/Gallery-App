package com.android.galleryapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.galleryapp.R
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.ui.components.AlbumGridItem
import com.android.galleryapp.ui.components.CustomTopAppBar
import com.android.galleryapp.ui.permissions.RequestStoragePermissions
import com.android.galleryapp.ui.theme.BrandingOrange
import com.android.galleryapp.ui.theme.SPACING_XXS
import com.android.galleryapp.viewmodel.SharedGalleryViewModel
import com.android.galleryapp.viewmodel.uistate.AlbumUiState

@Composable
fun AlbumScreen(viewModel: SharedGalleryViewModel = hiltViewModel()){

    val onPermissionsGranted: () -> Unit = {
       viewModel.fetchAlbums()
    }
    val errorMessage = stringResource(R.string.permission_not_granted)
    val onPermissionsDenied: () -> Unit = {

    }

    RequestStoragePermissions(
        onPermissionsGranted = onPermissionsGranted,
        onPermissionsDenied = onPermissionsDenied
    )

    val state by viewModel.uiState.collectAsState()

    when (state) {
        is AlbumUiState.Loading -> {
            CircularProgressIndicator()
        }
        is AlbumUiState.Success -> {
            val albums = (state as AlbumUiState.Success).albums
            Content(albums) { album ->
                viewModel.openDetailScreen(album)
            }
        }
        is AlbumUiState.Error -> {
            EmptyMediaState()
        }
    }
}

@Composable
private fun Content(albums: List<Album>, onGridItemClick: (album: Album) -> Unit) {

    Scaffold(
        backgroundColor =  BrandingOrange.v60,
        topBar = { CustomTopAppBar(title = stringResource(R.string.album_screen)) },
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMNS),
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            items(albums) { album ->
                AlbumGridItem(
                    album = album,
                    modifier = Modifier.padding(SPACING_XXS.dp)
                ){
                    onGridItemClick(album)
                }
            }
        }
    }
}

private const val GRID_COLUMNS = 2
