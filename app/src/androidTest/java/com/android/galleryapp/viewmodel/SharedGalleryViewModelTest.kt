package com.android.galleryapp.viewmodel

import android.net.Uri
import com.android.galleryapp.TestCoroutineRule
import com.android.galleryapp.data.model.Album
import com.android.galleryapp.data.model.MediaFile
import com.android.galleryapp.data.model.MediaType
import com.android.galleryapp.data.repository.GalleryRepository
import com.android.galleryapp.navigation.Destination
import com.android.galleryapp.navigation.Navigator
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SharedGalleryViewModelTest {

    @get:Rule
    val coroutineRule1 = TestCoroutineRule()

    private lateinit var viewModel: SharedGalleryViewModel
    private lateinit var repository: GalleryRepository
    private lateinit var navigator: Navigator

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        navigator = mockk(relaxed = true)
        viewModel = SharedGalleryViewModel(repository, navigator)
    }

    @Test
    fun fetchAlbumsShouldUpdateAlbumsFlowWithAlbumData() = runTest {
        // given
        val mockAlbums = listOf(
            Album("Album1", listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", MediaType.IMAGE))),
            Album("Album2", listOf(MediaFile(2, Uri.parse("/path/file2"), "file2", MediaType.VIDEO)))
        )
        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns mockk(relaxed = true)

        coEvery { repository.fetchAlbums() } returns flowOf(mockAlbums)

        // when
        viewModel.fetchAlbums()

        // then
        coVerify { repository.fetchAlbums() }
    }

    @Test
    fun fetchAlbumsShouldHandleEmptyAlbumList() = runTest {
        // given
        coEvery { repository.fetchAlbums() } returns flowOf(emptyList())

        viewModel.fetchAlbums()

        // when
       val resultList = viewModel.albumsFlow.value

        // then
        coVerify { repository.fetchAlbums() }
        assert(resultList.isEmpty())
    }

    @Test
    fun getMediaFilesForAlbumShouldReturnCorrectMediaFiles() {
        // given
        val album1 = Album("Album1", listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", MediaType.IMAGE)))
        val album2 = Album("Album2", listOf(MediaFile(2, Uri.parse("/path/file2"), "file2", MediaType.VIDEO)))

        // Set albumsFlow manually
        coEvery { repository.fetchAlbums() } returns flowOf(listOf(album1, album2))

        val viewModel = viewModel
        viewModel.fetchAlbums()

        // when
        val mediaFiles = viewModel.getMediaFilesForAlbum("Album1")

        // then
        assertEquals(1, mediaFiles?.size)
        assertEquals("file1", mediaFiles?.first()?.name)
    }

    @Test
    fun getMediaFilesForAlbumShouldReturnNullIfAlbumNotFound() {
        // given
        val album = Album("Album1", listOf(MediaFile(1, Uri.parse("/path/file1"), "file1", MediaType.IMAGE)))

        coEvery { repository.fetchAlbums() } returns flowOf(listOf(album))

        // when
        val mediaFiles = viewModel.getMediaFilesForAlbum("NonExistentAlbum")

        // then
        assertNull(mediaFiles)
    }

    @Test
    fun openDetailScreenShouldTriggerNavigation() = runTest {
        // given
        val album = Album("Album1", emptyList())

        // when
        viewModel.openDetailScreen(album)

        // then
        coVerify { navigator.navigate(Destination.DetailScreen("Album1")) }
    }
}

