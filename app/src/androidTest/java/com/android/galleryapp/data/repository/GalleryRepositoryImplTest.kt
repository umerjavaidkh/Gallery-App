package com.android.galleryapp.data.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GalleryRepositoryImplTest {

    private  var repository: GalleryRepositoryImpl = mockk(relaxed = true)
    private  var mockContext: Context =  mockk(relaxed = true)
    private  var mockContentResolver: ContentResolver = mockk(relaxed = true)
    private  var mockCursor: Cursor = mockk(relaxed = true)

    @Before
    fun setup() {

        System.setProperty("mockk.inline.mocking.enabled", "false")

        every { mockContext.contentResolver } returns mockContentResolver

        repository = GalleryRepositoryImpl(mockContext)
    }

    @Test
    fun testFetchAlbums() {

    }

}