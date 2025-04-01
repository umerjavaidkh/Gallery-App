package com.android.galleryapp.data.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.data.util.shouldExcludeFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class GalleryRepositoryImpl(private val context: Context): GalleryRepository {

    override suspend fun fetchAlbums(): Flow<List<Album>> = flow {
        val albums = mutableMapOf<String, MutableList<MediaFile>>()
        val allImages = mutableListOf<MediaFile>()
        val allVideos = mutableListOf<MediaFile>()

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA
        )

        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE}=? OR ${MediaStore.Files.FileColumns.MEDIA_TYPE}=?"
        val selectionArgs = arrayOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE.toString(),
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()
        )

        val cursor = context.contentResolver.query(
            MediaStore.Files.getContentUri(EXTERNAL_TAG),
            projection, selection, selectionArgs, null
        )

        cursor?.use {
            val idColumn = it.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val nameColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val pathColumn = it.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            val typeColumn = it.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val name = it.getString(nameColumn)
                val path = it.getString(pathColumn)
                val type =
                    if (it.getInt(typeColumn) == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
                        MediaType.IMAGE else MediaType.VIDEO

                // Exclude cache, thumbnails, and .media files
                if (shouldExcludeFile(path)) continue

                val uri = ContentUris.withAppendedId(MediaStore.Files.getContentUri(EXTERNAL_TAG), id)
                val folderName = File(path).parentFile?.name ?: UNKNOWN_BUCKET

                // Add to the respective folder album
                albums.getOrPut(folderName) { mutableListOf() }
                    .add(
                        if(type == MediaType.VIDEO){
                            val thumbnail = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                            MediaFile(id, uri, name, type, thumbnail)
                        }
                        else
                        MediaFile(id, uri, name, type)
                    )

                // Add to "All Images" or "All Videos"
                if (type == MediaType.IMAGE) {
                    allImages.add(MediaFile(id, uri, name, type))
                } else {
                    val thumbnail = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    allVideos.add(MediaFile(id, uri, name, type, thumbnail))
                }

            } // End of cursor loop
        }// End of cursor use

        // Add "All Images" and "All Videos" albums at the top
        val albumList = mutableListOf<Album>()
        if (allImages.isNotEmpty()) albumList.add(Album(ALL_IMAGES_TAG_NAME, allImages))
        if (allVideos.isNotEmpty()) albumList.add(Album(ALL_VIDEOS_TAG_NAME, allVideos))

        // Add all other albums
        albumList.addAll(albums.map { Album(it.key, it.value) })

        emit(albumList)
    }

    private fun geThumbnail(album: MediaFile): Uri {

        val baseUri = if (album.type == MediaType.VIDEO) {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        return ContentUris.withAppendedId(baseUri, album.id)
    }
}

const val ALL_VIDEOS_TAG_NAME = "All Videos"
const val ALL_IMAGES_TAG_NAME = "All Images"
const val UNKNOWN_BUCKET = "Unknown"
const val EXTERNAL_TAG = "external"