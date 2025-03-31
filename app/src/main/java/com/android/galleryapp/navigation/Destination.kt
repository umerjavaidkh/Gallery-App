package com.android.galleryapp.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    val name : String

    @Serializable
      object AlbumScreen: Destination {
        override val name: String
            get() = "AlbumScreen"
    }

    @Serializable
    data class DetailScreen(val albumName: String): Destination {
        override val name: String
            get() = "DetailScreen"
    }
}