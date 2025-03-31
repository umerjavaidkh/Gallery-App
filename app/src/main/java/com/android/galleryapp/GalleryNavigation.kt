package com.android.galleryapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.galleryapp.navigation.Destination
import com.android.galleryapp.ui.DetailScreen
import com.android.galleryapp.ui.AlbumScreen
import com.android.galleryapp.viewmodel.SharedGalleryViewModel

@Composable
fun GalleryNavHost(navController: NavHostController) {

    val sharedViewModel: SharedGalleryViewModel = hiltViewModel()

    NavHost(navController, startDestination = Destination.AlbumScreen.name) {
        composable(Destination.AlbumScreen.name) { AlbumScreen(sharedViewModel) }
        val destinationScreen = "$DETAIL_SCREEN/{$ALBUM_NAME}"
        composable(destinationScreen) { backStackEntry ->
            val albumName = backStackEntry.arguments?.getString(ALBUM_NAME) ?: ""
            DetailScreen(albumName, sharedViewModel)
        }
    }
}

private  const val DETAIL_SCREEN = "DetailScreen"
private  const val ALBUM_NAME = "albumName"