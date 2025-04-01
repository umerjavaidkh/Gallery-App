package com.android.galleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.galleryapp.navigation.Destination
import com.android.galleryapp.navigation.NavigationAction
import com.android.galleryapp.navigation.Navigator
import com.android.galleryapp.navigation.ObserveAsEvents
import com.android.galleryapp.ui.theme.GalleryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            GalleryTheme {

                val navController = rememberNavController()

                HandleCommands(navController)

                GalleryNavHost(navController)
            }
        }
    }

    @Composable
    private fun HandleCommands(navController: NavHostController) {
        ObserveAsEvents(flow = navigator.navigationActions) { action ->
            when (action) {
                is NavigationAction.Navigate -> {
                    // Below portion could be handled by Navigation 3 latest in best way
                    when (action.destination.name) {
                        Destination.AlbumScreen.name -> navController.navigate(Destination.AlbumScreen.name)
                        else -> {
                            val actionRoute = action.destination as Destination.DetailScreen
                            navController.navigate("${actionRoute.name}/${actionRoute.albumName}")
                        }
                    }
                }
                NavigationAction.NavigateUp -> navController.navigateUp()
            }
        }
    }

}