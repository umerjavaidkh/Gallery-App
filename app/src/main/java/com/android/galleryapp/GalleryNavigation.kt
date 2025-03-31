package com.android.galleryapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.galleryapp.ui.AlbumScreen
import com.android.galleryapp.ui.DetailScreen

@Composable
fun GalleryNavHost(navController: NavHostController) {

    NavHost(navController, startDestination = "albumScreen") {
        composable("albumScreen") { AlbumScreen() }
        composable("mediaGrid/{albumName}") { DetailScreen(navController) }
    }
}