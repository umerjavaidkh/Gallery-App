package com.android.galleryapp.data.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.galleryapp.data.util.shouldExcludeFile
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.TestCase.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GalleryRepositoryImplTest {

    private  var repository = mockk<GalleryRepositoryImpl>(relaxed = true) as GalleryRepositoryImpl
    private  var mockContext: Context =  mockk(relaxed = true)
    private  var mockContentResolver: ContentResolver = mockk(relaxed = true)
    private  var mockCursor: Cursor = mockk(relaxed = true)

    @Before
    fun setup() {
        every { mockContext.contentResolver } returns mockContentResolver
        repository = GalleryRepositoryImpl(mockContext)
    }

    @Test
     fun testFetchAlbums_returnsExpectedAlbums() = runTest {

        // Define mock projection
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA
        )

        // Mock the expected cursor query
        every {
            mockContentResolver.query(
                MediaStore.Files.getContentUri(EXTERNAL_TAG),
                projection,
                any(),
                any(),
                any()
            )
        } returns mockCursor


        // Mock cursor behavior with fake media data
        every { mockCursor.moveToNext() } returnsMany listOf(true, true, false) // Two rows
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns._ID) } returns 0
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE) } returns 1
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME) } returns 2
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA) } returns 3

        every { mockCursor.getLong(0) } returnsMany listOf(100L, 101L) // Fake media IDs
        every { mockCursor.getInt(1) } returnsMany listOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
        )
        every { mockCursor.getString(2) } returnsMany listOf("Image1.jpg", "Video1.mp4")
        every { mockCursor.getString(3) } returnsMany listOf("/storage/emulated/0/DCIM/Image1.jpg", "/storage/emulated/0/DCIM/Video1.mp4")

        // Mock the top-level function shouldExcludeFile from FileHelper.kt
        mockkStatic("com.android.galleryapp.data.util.FileHelperKt") // The fully qualified name of the file

        // Mock shouldExcludeFile() to always return false for any input
        every { shouldExcludeFile(any()) } returns false

        // Run repository function and collect results
        val albums = repository.fetchAlbums().first()

        // Assertions
        assertEquals(3, albums.size)
        assertEquals(ALL_IMAGES_TAG_NAME, albums[0].name)
        assertEquals(ALL_VIDEOS_TAG_NAME, albums[1].name)
        assertEquals(1, albums[0].media?.size) // One image
        assertEquals(1, albums[1].media?.size) // One video

        verify { mockCursor.close() } // Ensure the cursor is closed
    }

    @Test
    fun testFetchAlbums_returnsEmptyListWhenNoMediaFiles() = runTest {
        // Mock the query returning an empty cursor
        every { mockContentResolver.query(any(), any(), any(), any(), any()) } returns mockCursor
        every { mockCursor.moveToNext() } returns false // No data

        val albums = repository.fetchAlbums().first()

        assertTrue(albums.isEmpty()) // Expecting an empty list

        verify { mockCursor.close() }
    }

    @Test
    fun testFetchAlbums_groupsFilesIntoSameAlbum() = runTest {
        every { mockContentResolver.query(any(), any(), any(), any(), any()) } returns mockCursor
        every { mockCursor.moveToNext() } returnsMany listOf(true, true, false) // Two rows

        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns._ID) } returns 0
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE) } returns 1
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME) } returns 2
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA) } returns 3

        every { mockCursor.getLong(0) } returnsMany listOf(100L, 101L)
        every { mockCursor.getInt(1) } returnsMany listOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
        )
        every { mockCursor.getString(2) } returnsMany listOf("Image1.jpg", "Image2.jpg")
        every { mockCursor.getString(3) } returnsMany listOf(
            "/storage/emulated/0/DCIM/MyAlbum/Image1.jpg",
            "/storage/emulated/0/DCIM/MyAlbum/Image2.jpg"
        )

        // Mock the top-level function shouldExcludeFile from FileHelper.kt
        mockkStatic("com.android.galleryapp.data.util.FileHelperKt") // The fully qualified name of the file

        // Mock shouldExcludeFile() to always return false for any input
        every { shouldExcludeFile(any()) } returns false

        val albums = repository.fetchAlbums().first()

        assertEquals(2, albums.size) // "All Images" + "MyAlbum"
        assertEquals(ALL_IMAGES_TAG_NAME, albums[0].name)
        assertEquals("MyAlbum", albums[1].name)
        assertEquals(2, albums[1].media?.size) // Both images in same album

        verify { mockCursor.close() }
    }

    @Test
    fun testFetchAlbums_excludesUnwantedFiles() = runTest {
        every { mockContentResolver.query(any(), any(), any(), any(), any()) } returns mockCursor
        every { mockCursor.moveToNext() } returnsMany listOf(true, true, false) // Two rows

        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns._ID) } returns 0
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE) } returns 1
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME) } returns 2
        every { mockCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA) } returns 3

        every { mockCursor.getLong(0) } returnsMany listOf(100L, 101L)
        every { mockCursor.getInt(1) } returnsMany listOf(
            MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE,
            MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
        )
        every { mockCursor.getString(2) } returnsMany listOf("Image1.jpg", "Video1.mp4")
        every { mockCursor.getString(3) } returnsMany listOf(
            "/storage/emulated/0/DCIM/Image1.jpg",
            "/storage/emulated/0/DCIM/cache/Video1.mp4" // Cached file
        )

        // Mock the top-level function shouldExcludeFile from FileHelper.kt
        mockkStatic("com.android.galleryapp.data.util.FileHelperKt") // The fully qualified name of the file

        // Exclude the second file
        every { shouldExcludeFile("/storage/emulated/0/DCIM/Image1.jpg") } returns false
        every { shouldExcludeFile("/storage/emulated/0/DCIM/cache/Video1.mp4") } returns true

        val albums = repository.fetchAlbums().first()

        assertEquals(ALL_IMAGES_TAG_NAME, albums[0].name)
        assertEquals(1, albums[0].media?.size) // Only one image should be in the album

        verify { mockCursor.close() }
    }

}